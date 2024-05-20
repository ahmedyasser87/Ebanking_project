package ma.emsi.ebankingbackend;

import ma.emsi.ebankingbackend.Exceptions.BankAccountNotFoundException;
import ma.emsi.ebankingbackend.Exceptions.BanlanceNotSufficientException;
import ma.emsi.ebankingbackend.Exceptions.CustomerNotFoundException;
import ma.emsi.ebankingbackend.dtos.BankAccountDTO;
import ma.emsi.ebankingbackend.dtos.CurrentBankAccountDTO;
import ma.emsi.ebankingbackend.dtos.CustomerDTO;
import ma.emsi.ebankingbackend.dtos.SavingBankAccountDTO;
import ma.emsi.ebankingbackend.entities.*;
import ma.emsi.ebankingbackend.enums.AccountStatus;
import ma.emsi.ebankingbackend.enums.OperationType;
import ma.emsi.ebankingbackend.repositories.CustomerRepository;
import ma.emsi.ebankingbackend.repositories.repositories.AccountOperationRepository;
import ma.emsi.ebankingbackend.repositories.repositories.BankAccountRepository;
import ma.emsi.ebankingbackend.services.BankAccountService;
import ma.emsi.ebankingbackend.services.BankService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EBankingBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EBankingBackendApplication.class, args);
    }
    @Bean

    CommandLineRunner commandLineRunner(BankAccountService bankAccountService)
    {
        return args ->
        {
        Stream.of("Hassan","Imane","Mohamed").forEach(name->{
            CustomerDTO customerDTO=new CustomerDTO();
            customerDTO.setName(name);
            customerDTO.setEmail(name+"@gmail.com");
            bankAccountService.saveCustomer(customerDTO);
        });
        bankAccountService.listCustomers().forEach(customer->
        {
            try {
                bankAccountService.saveCurrentBankAccount(Math.random() * 90000, 9000, customer.getId());
                bankAccountService.saveSavingBankAccount(Math.random() * 9000, 5.5, customer.getId());

            } catch(CustomerNotFoundException e)
            {
                e.printStackTrace();

            }


        });
            List<BankAccountDTO> bankAccounts = bankAccountService.bankAccountList();
            for (BankAccountDTO bankAccount : bankAccounts) {
                for (int i= 0; i < 10; i++) {
                    String accountId;
                    if (bankAccount instanceof SavingBankAccountDTO) {
                        accountId = ((SavingBankAccountDTO) bankAccount).getId();


                    } else {
                        accountId = ((CurrentBankAccountDTO) bankAccount).getId();

                    }
                    bankAccountService.credit(accountId, 100000 + Math.random(), "Credit");
                    bankAccountService.debit(accountId, 1000 + Math.random() * 9000, "debit");


                }
            }

        };
    }
    //@Bean
    CommandLineRunner start(CustomerRepository customerRepository ,
                            BankAccountRepository bankAccountRepository,
                            AccountOperationRepository accountOperationRepository)
    {
        return args -> {
            Stream.of("Hassan","Yassine","Aicha").forEach(name->{
                Customer customer=new Customer();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");
                customerRepository.save(customer);
            });
            customerRepository.findAll().forEach(cust->

            {
                SavingAccount savingAccount=new SavingAccount();
                savingAccount.setId(UUID.randomUUID().toString());
                savingAccount.setBalance(Math.random()*900);
                savingAccount.setCreatedAt(new Date());
                savingAccount.setStatus(AccountStatus.CREATED);
                savingAccount.setCustomer(cust);
                savingAccount.setInterestRate(5.5);
                bankAccountRepository.save(savingAccount);
                CurrentAccount currentAccount=new CurrentAccount();
                currentAccount.setId(UUID.randomUUID().toString());
                currentAccount.setBalance(Math.random()*900);
                currentAccount.setCreatedAt(new Date());
                currentAccount.setStatus(AccountStatus.CREATED);
                currentAccount.setCustomer(cust);
                currentAccount.setOverDraft(9000);
                bankAccountRepository.save(currentAccount);

            });
            bankAccountRepository.findAll().forEach(acc->{
                for (int i =0; i<10; i++) {
                    AccountOperation accountOperation=new AccountOperation();
                    accountOperation.setOperationDate(new Date());
                    accountOperation.setAmount (Math.random()*12000);
                    accountOperation.setType(Math.random()>0.5? OperationType.DEBIT: OperationType.CREDIT);
                    accountOperation.setBankAccount (acc);
                    accountOperationRepository.save(accountOperation);
                }
                BankAccount bankAccount=
                        bankAccountRepository.findById("").orElse(null);
                if(bankAccount!=null) {
                    System.out.println("**************************");
                    System.out.println(bankAccount.getId());
                    System.out.println(bankAccount.getBalance());
                    System.out.println(bankAccount.getStatus());
                    System.out.println(bankAccount.getCreatedAt());
                    System.out.println(bankAccount.getCustomer().getName());
                    System.out.println(bankAccount.getClass().getSimpleName());
                    if (bankAccount instanceof CurrentAccount) {
                        System.out.println("OverDraft =>" + ((CurrentAccount) bankAccount).getOverDraft());
                    } else if (bankAccount instanceof SavingAccount) {
                        ((SavingAccount) bankAccount).getInterestRate();
                    }
                    bankAccount.getAccountOperations().forEach(op ->
                    {
                        System.out.println("========================");
                        System.out.println(op.getType());
                        System.out.println(op.getAmount());
                        System.out.println(op.getOperationDate());


                    });
                }



            });

        };
    }

}

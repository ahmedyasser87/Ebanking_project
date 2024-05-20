package ma.emsi.ebankingbackend.services;

import ma.emsi.ebankingbackend.entities.BankAccount;
import ma.emsi.ebankingbackend.entities.CurrentAccount;
import ma.emsi.ebankingbackend.entities.SavingAccount;
import ma.emsi.ebankingbackend.repositories.repositories.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BankService {
    @Autowired
    private BankAccountRepository bankAccountRepository;

    public void consulter() {
        BankAccount bankAccount = bankAccountRepository.findById("531889d4-4292-448f-9bf2-ff5773e24813").orElse(null);
        if (bankAccount != null) {
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
    }
}

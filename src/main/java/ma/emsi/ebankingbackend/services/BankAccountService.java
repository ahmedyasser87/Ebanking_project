package ma.emsi.ebankingbackend.services;

import ma.emsi.ebankingbackend.Exceptions.BankAccountNotFoundException;
import ma.emsi.ebankingbackend.Exceptions.BanlanceNotSufficientException;
import ma.emsi.ebankingbackend.Exceptions.CustomerNotFoundException;
import ma.emsi.ebankingbackend.dtos.*;
import ma.emsi.ebankingbackend.entities.BankAccount;
import ma.emsi.ebankingbackend.entities.CurrentAccount;
import ma.emsi.ebankingbackend.entities.Customer;
import ma.emsi.ebankingbackend.entities.SavingAccount;

import java.util.List;

public interface BankAccountService {
   CustomerDTO saveCustomer(CustomerDTO customerDTO);
   CurrentBankAccountDTO saveCurrentBankAccount (double initialBalance, double overDraft  , Long customerId) throws CustomerNotFoundException;
   SavingBankAccountDTO saveSavingBankAccount (double initialBalance, double interestRate  , Long customerId) throws CustomerNotFoundException;
   List<CustomerDTO> listCustomers();
   BankAccountDTO getBankAccount(String accountId ) throws BankAccountNotFoundException;
   void debit(String accountId,double amount,String description ) throws BankAccountNotFoundException, BanlanceNotSufficientException;
   void credit(String accountId,double amount,String description) throws BankAccountNotFoundException, BanlanceNotSufficientException;
   void transfer(String accountIdSource, String accountIdDestination, double amount ) throws BankAccountNotFoundException, BanlanceNotSufficientException;

   List<BankAccountDTO> bankAccountList();

   CustomerDTO getCustomer(Long cutomerId) throws CustomerNotFoundException;

   CustomerDTO updateCustomer(CustomerDTO customerDTO);

   void deleteCustomer(Long customerId);

   List<AccountOperationDTO> accountHistory(String accountId);

   AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException;
}

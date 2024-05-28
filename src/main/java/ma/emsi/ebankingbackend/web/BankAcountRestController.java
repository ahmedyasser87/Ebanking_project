package ma.emsi.ebankingbackend.web;

import ma.emsi.ebankingbackend.Exceptions.BankAccountNotFoundException;
import ma.emsi.ebankingbackend.Exceptions.BanlanceNotSufficientException;
import ma.emsi.ebankingbackend.dtos.*;
import ma.emsi.ebankingbackend.services.BankAccountService;
import ma.emsi.ebankingbackend.services.BankService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("*")
@RestController

public class BankAcountRestController {
    private BankAccountService bankAccountService;

    public BankAcountRestController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }


    @GetMapping("/accounts/{accountId}")
    public BankAccountDTO getBankAccount(@PathVariable String accountId) throws BankAccountNotFoundException {
        return bankAccountService.getBankAccount(accountId);
    }

    @GetMapping("/accounts")
    public List<BankAccountDTO> listAccounts() {
        return bankAccountService.bankAccountList();
    }

    @GetMapping("/accounts/{accountId}/operations")
    public List<AccountOperationDTO> getHistory(@PathVariable String accountId) {
        return bankAccountService.accountHistory(accountId);
    }
    @GetMapping("/accounts/{accountId}/pageOperations")
    public AccountHistoryDTO getAccountHistory(@PathVariable String accountId, @RequestParam(name = "page",defaultValue = "0") int page , @RequestParam(name = "size",defaultValue = "5")int size) throws BankAccountNotFoundException {
        return bankAccountService.getAccountHistory(accountId,page,size);
    }
@PostMapping("/accounts/debit")
public DebitDTO debit( @RequestBody DebitDTO debitDTO) throws BankAccountNotFoundException, BanlanceNotSufficientException {
    this.bankAccountService.debit(debitDTO.getAccountID(),debitDTO.getAmount(),debitDTO.getDescription());
   return debitDTO;
    }
    @PostMapping("/accounts/credit")
    public CreditDTO credit(@RequestBody CreditDTO creditDTO) throws BankAccountNotFoundException, BanlanceNotSufficientException {
        this.bankAccountService.credit(creditDTO.getAccountID(),creditDTO.getAmount(),creditDTO.getDescription());
        return creditDTO;
    }
    @PostMapping("/accounts/transfer")
    public void transfer(@RequestBody TransferRequestDTO transferRequestDTO) throws BankAccountNotFoundException, BanlanceNotSufficientException {
        this.bankAccountService.transfer(transferRequestDTO.getAccountSource()
                , transferRequestDTO.getAccountDestination(),
                transferRequestDTO.getAmount());

    }

}

package ma.emsi.ebankingbackend.web;

import ma.emsi.ebankingbackend.Exceptions.BankAccountNotFoundException;
import ma.emsi.ebankingbackend.dtos.AccountHistoryDTO;
import ma.emsi.ebankingbackend.dtos.AccountOperationDTO;
import ma.emsi.ebankingbackend.dtos.BankAccountDTO;
import ma.emsi.ebankingbackend.services.BankAccountService;
import ma.emsi.ebankingbackend.services.BankService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public AccountHistoryDTO getAccountHistory(@PathVariable String accountId, @RequestParam(name = "page",defaultValue = "0") int page , @RequestParam(name = "page",defaultValue = "5")int size) throws BankAccountNotFoundException {
        return bankAccountService.getAccountHistory(accountId,page,size);
    }



}

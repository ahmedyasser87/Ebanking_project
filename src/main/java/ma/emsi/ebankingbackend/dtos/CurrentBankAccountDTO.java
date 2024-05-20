package ma.emsi.ebankingbackend.dtos;

import lombok.Data;
import ma.emsi.ebankingbackend.Exceptions.BanlanceNotSufficientException;
import ma.emsi.ebankingbackend.enums.AccountStatus;

import java.util.Date;


@Data


public class CurrentBankAccountDTO extends BankAccountDTO
{

    private String id ;
    private Double balance ;
    private Date createdAt;

    private AccountStatus status ;

    private CustomerDTO customerDTO;
    private  double overDraft;







}

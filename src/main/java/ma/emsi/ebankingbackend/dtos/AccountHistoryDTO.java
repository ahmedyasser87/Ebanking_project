package ma.emsi.ebankingbackend.dtos;

import lombok.Data;

import java.util.List;
@Data

public class AccountHistoryDTO {


    private List<AccountOperationDTO> accountOperationDTOList;
    private double balance;
    private int currentPage;
    private int totalPages;
    private int pageSize;

    private String accountId;

}

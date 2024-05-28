package ma.emsi.ebankingbackend.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.emsi.ebankingbackend.Exceptions.CustomerNotFoundException;
import ma.emsi.ebankingbackend.dtos.CustomerDTO;
import ma.emsi.ebankingbackend.entities.Customer;
import ma.emsi.ebankingbackend.services.BankAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("*")
@RestController
@AllArgsConstructor
@Slf4j



public class CustomerRestController {
    private BankAccountService bankAccountService;
@GetMapping("/customers")
    public List<CustomerDTO> customers()
    {
        return bankAccountService.listCustomers();

    }
    @GetMapping("/customers/search")
    public List<CustomerDTO> searchCustomers(@RequestParam(name="keyword",defaultValue = "") String keyword)
    {
        return bankAccountService.searchCustomers(keyword);

    }
    @GetMapping("/customers/{id}")
    public CustomerDTO getCustomer( @PathVariable(name = "id") Long customerId) throws CustomerNotFoundException {
   return bankAccountService.getCustomer(customerId);
    }
    @PostMapping("/customers")
    public  CustomerDTO saveCustomer( @RequestBody CustomerDTO customerDTO)
    {
        return bankAccountService.saveCustomer(customerDTO);

    }
    @PutMapping("/customers/{cutomerId}")
    public CustomerDTO updateCustomer (@PathVariable Long cutomerId , @RequestBody CustomerDTO customerDTO)

    {
        customerDTO.setId(cutomerId);
      return   bankAccountService.updateCustomer(customerDTO);


    }
    @DeleteMapping("/customers/{id}")
    public void deleteCustomer(@PathVariable Long id )
    {
        bankAccountService.deleteCustomer(id);
    }
}

package ma.emsi.ebankingbackend.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.emsi.ebankingbackend.Exceptions.CustomerNotFoundException;
import ma.emsi.ebankingbackend.dtos.CustomerDTO;
import ma.emsi.ebankingbackend.entities.Customer;
import ma.emsi.ebankingbackend.services.BankAccountService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("*")
@RestController
@AllArgsConstructor
@Slf4j



public class CustomerRestController {
    private BankAccountService bankAccountService;
@GetMapping("/customers")
@PreAuthorize("hasAuthority('SCOPE_USER')")
    public List<CustomerDTO> customers()
    {
        return bankAccountService.listCustomers();

    }
    @GetMapping("/customers/search")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public List<CustomerDTO> searchCustomers(@RequestParam(name="keyword",defaultValue = "") String keyword)
    {
        return bankAccountService.searchCustomers(keyword);

    }
    @GetMapping("/customers/{id}")@PreAuthorize("hasAuthority('SCOPE_USER')")

    public CustomerDTO getCustomer( @PathVariable(name = "id") Long customerId) throws CustomerNotFoundException {
   return bankAccountService.getCustomer(customerId);
    }
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PostMapping("/customers")

    public  CustomerDTO saveCustomer( @RequestBody CustomerDTO customerDTO)
    {
        return bankAccountService.saveCustomer(customerDTO);

    }
    @PutMapping("/customers/{cutomerId}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public CustomerDTO updateCustomer (@PathVariable Long cutomerId , @RequestBody CustomerDTO customerDTO)

    {
        customerDTO.setId(cutomerId);
      return   bankAccountService.updateCustomer(customerDTO);


    }
    @DeleteMapping("/customers/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public void deleteCustomer(@PathVariable Long id )
    {
        bankAccountService.deleteCustomer(id);
    }
}

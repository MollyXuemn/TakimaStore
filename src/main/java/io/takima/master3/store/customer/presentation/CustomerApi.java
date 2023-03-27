package io.takima.master3.store.customer.presentation;

import io.takima.master3.store.core.pagination.PageSearch;
import io.takima.master3.store.core.pagination.SearchSpecification;
import io.takima.master3.store.customer.models.Customer;
import io.takima.master3.store.customer.services.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/customers")
@AllArgsConstructor
public class CustomerApi {
    private final CustomerService customerService;

    @GetMapping(value = "", produces = "application/json")
    public Page<Customer> findPage(@RequestParam(defaultValue = "20")int limit,
                                   @RequestParam(defaultValue = "0")int offset,
                                   @RequestParam(required = false) String search,
                                   @SortDefault Sort sort) {
        Specification<Customer> spec = (search != null) ? SearchSpecification.parse(search) : Specification.where(null);
        return customerService.findPage(new PageSearch.Builder<Customer>()
                .limit(limit)
                .offset(offset)
                .search(spec)
                .sort(sort).build());
    }
    @GetMapping(value = "/{id}", produces = "application/json")
    public Customer getCustomer(@PathVariable long id) {
        return customerService.findById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("no customer with id %d", id)));
    }

    @PostMapping(value = "", produces = "application/json")
    public Customer create(@RequestBody Customer customer) {
        if (customer.getId() != null) {
            throw new IllegalArgumentException("cannot create a customer and specify the ID");
        }
        customerService.create(customer);

        return customer;
    }

    @PutMapping(value = "", produces = "application/json")
    public Customer update(@RequestBody() Customer customer) {
        if (customer.getId() == null) {
            throw new IllegalArgumentException("did not specify the ID of customer to update");
        }
        customerService.update(customer);

        return customer;
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public void delete(@PathVariable long id) {
        customerService.deleteById(id);
    }
}

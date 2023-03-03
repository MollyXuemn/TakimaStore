package io.takima.master3.store.customer.presentation;

import io.takima.master3.store.customer.models.Customer;
import io.takima.master3.store.customer.services.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/api")
@AllArgsConstructor
public class CustomerApi {

    private final CustomerService customerService;

    // TODO would need a refactor to be more RESTful. This is the topic of the REST milestone.
    @ResponseBody
    @GetMapping(value = "/getAllCustomers", produces = "application/json")
    public List<Customer> findPage(@RequestParam(required = false, defaultValue = "") String search,
                                  @RequestParam(required = false, defaultValue = "20") int limit,
                                  @RequestParam(required = false, defaultValue = "0") int offset) {

        return customerService.findPage(search, limit, offset);
    }

    // TODO would need a refactor to be more RESTful. This is the topic of the REST milestone.
    @ResponseBody
    @GetMapping(value = "/getCustomer", produces = "application/json")
    public Customer getCustomer(@RequestParam() long id) {
        return customerService.findById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("no customer with id %d", id)));
    }

    // TODO would need a refactor to be more RESTful. This is the topic of the REST milestone.
    @ResponseBody
    @PostMapping(value = "/createCustomer", produces = "application/json")
    public Customer create(@RequestBody() Customer customer) {
        if (customer.getId() != null) {
            throw new IllegalArgumentException("cannot create a customer and specify the ID");
        }

        customerService.create(customer);

        return customer;
    }

    // TODO would need a refactor to be more RESTful. This is the topic of the REST milestone.
    @ResponseBody
    @PostMapping(value = "/updateCustomer", produces = "application/json")
    public Customer update(@RequestBody() Customer customer) {
        if (customer.getId() == null) {
            throw new IllegalArgumentException("did not specify the ID of customer to update");
        }

        customerService.update(customer);

        return customer;
    }

    // TODO would need a refactor to be more RESTful. This is the topic of the REST milestone.
    @ResponseBody
    @GetMapping(value = "/deleteCustomer", produces = "application/json")
    public void delete(@RequestParam() long id) {
        customerService.deleteById(id);
    }
}

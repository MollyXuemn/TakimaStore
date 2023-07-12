package io.takima.master3.store.customer.presentation;

import com.fasterxml.jackson.annotation.JsonView;
import io.takima.master3.store.core.converters.models.PageableAsQueryParam;
import io.takima.master3.store.core.pagination.PageSearch;
import io.takima.master3.store.core.pagination.PageSearchDefault;
import io.takima.master3.store.customer.models.Customer;
import io.takima.master3.store.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/customers", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerApi {

    private final CustomerService customerService;

    @Autowired
    CustomerApi(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    @JsonView(Customer.Views.LIGHT.class)
    @PageableAsQueryParam
    public Page<Customer> getAll(@PageSearchDefault PageSearch<Customer> pageSearch) {
        return customerService.findAll(pageSearch);
    }

    @GetMapping("{id}")
    @JsonView(Customer.Views.FULL.class)
    public Customer getOne(@PathVariable long id) {
        return customerService.findById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("no customer with id %d", id)));
    }

    @PostMapping
    @JsonView(Customer.Views.ID.class)
    public ResponseEntity<EntityModel<Customer>> createOne(@RequestBody() Customer customer) {
        if (customer.getId() != null) {
            throw new IllegalArgumentException("cannot create a customer and specify the ID");
        }

        customerService.create(customer);

        return new ResponseEntity<>(new CustomerRepresentationModelAssembler().toModel(customer), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<EntityModel<Customer>> updateOne(@RequestBody() Customer customer) {
        if (customer.getId() == null) {
            throw new IllegalArgumentException("did not specify the ID of customer to update");
        }

        customerService.update(customer);

        return new ResponseEntity<>(new CustomerRepresentationModelAssembler().toModel(customer), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public void deleteOne(@PathVariable long id) {
        customerService.deleteById(id);
    }
}

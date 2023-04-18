package io.takima.master3.store.customer.presentation;

import com.fasterxml.jackson.annotation.JsonView;
import io.takima.master3.store.cart.presentation.CartApi;
import io.takima.master3.store.core.pagination.PageSearch;
import io.takima.master3.store.core.pagination.SearchSpecification;
import io.takima.master3.store.customer.models.Customer;
import io.takima.master3.store.customer.services.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.SortDefault;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.NoSuchElementException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin
@RequestMapping("/api/customers")
@AllArgsConstructor
public class CustomerApi {
    private final CustomerService customerService;
    private final CustomerRepresentationModelAssembler assembler;

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
    public ResponseEntity<EntityModel<Customer>> getCustomer(@PathVariable long id) {
        Customer customer=customerService.findById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("no customer with id %d", id)));
        return new ResponseEntity<>(assembler.toModel(customer), HttpStatus.OK);
    }
    @JsonView(Customer.Views.ID.class)
    @PostMapping(value = "", produces = "application/json")
    public ResponseEntity<EntityModel<Customer>> create(@RequestBody Customer customer) {

        if (customer.getId() != null) {
            throw new IllegalArgumentException("cannot create a customer and specify the ID");
        }
        customerService.create(customer);

        return new ResponseEntity<>(assembler.toModel(customer), HttpStatus.CREATED);
    }

    @PutMapping(value = "", produces = "application/json")
    public ResponseEntity<EntityModel<Customer>> update(@RequestBody() Customer customer) {
        if (customer.getId() == null) {
            throw new IllegalArgumentException("did not specify the ID of customer to update");
        }
        customerService.update(customer);

        return new ResponseEntity<>(assembler.toModel(customer), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public void delete(@PathVariable long id) {
        customerService.deleteById(id);
    }
}
@Component
class CustomerRepresentationModelAssembler implements RepresentationModelAssembler<Customer, EntityModel<Customer>> {
    public EntityModel<Customer> toModel(Customer customer) {
        Link selfLink = linkTo(methodOn(CustomerApi.class).getCustomer(customer.getId())).withSelfRel();
        Link cartLink = linkTo(methodOn(CartApi.class).getCart(customer.getId())).withRel("cart");
        return EntityModel.of(customer, selfLink, cartLink);
    }
}


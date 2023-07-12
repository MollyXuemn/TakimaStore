package io.takima.master3.store.customer.presentation;

import io.takima.master3.store.cart.presentation.CartApi;
import io.takima.master3.store.customer.models.Customer;
import org.jetbrains.annotations.NotNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CustomerRepresentationModelAssembler implements RepresentationModelAssembler<Customer, EntityModel<Customer>> {
    public @NotNull EntityModel<Customer> toModel(Customer customer) {
        Link selfLink = linkTo(methodOn(CustomerApi.class).getOne(customer.getId())).withSelfRel();
        Link cartLink = WebMvcLinkBuilder.linkTo(methodOn(CartApi.class).getCart(customer.getId())).withRel("cart");
        return EntityModel.of(customer, selfLink, cartLink);
    }
}


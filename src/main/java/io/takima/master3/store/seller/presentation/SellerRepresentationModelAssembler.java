package io.takima.master3.store.seller.presentation;

import io.takima.master3.store.seller.models.Seller;
import org.jetbrains.annotations.NotNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class SellerRepresentationModelAssembler implements RepresentationModelAssembler<Seller, EntityModel<Seller>> {
    public @NotNull EntityModel<Seller> toModel(Seller seller) {
        Link selfLink = linkTo(methodOn(SellerApi.class).getOne(seller.getId())).withSelfRel();
        Link articleLink = linkTo(methodOn(SellerApi.class).getAllArticles(seller.getId())).withRel("article");
        return EntityModel.of(seller, selfLink, articleLink);
    }
}

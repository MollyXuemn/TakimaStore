package io.takima.master3.store.seller.presentation;

import com.fasterxml.jackson.annotation.JsonView;
import io.takima.master3.store.article.models.Article;
import io.takima.master3.store.article.service.ArticleService;
import io.takima.master3.store.cart.presentation.CartApi;
import io.takima.master3.store.cart.presentation.CartDTO;
import io.takima.master3.store.core.pagination.PageSearch;
import io.takima.master3.store.core.pagination.SearchSpecification;
import io.takima.master3.store.customer.models.Customer;
import io.takima.master3.store.customer.presentation.CustomerApi;
import io.takima.master3.store.seller.models.Seller;
import io.takima.master3.store.seller.service.SellerService;
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
@RequestMapping("/api/sellers")
@AllArgsConstructor
public class SellerApi {
    private final SellerService sellerService;
    private final ArticleService articleService;
    private final SellerRepresentationModelAssembler assembler;
    @JsonView(Seller.Views.LIGHT.class)
    @GetMapping(value = "", produces = "application/json")
    public Page<Seller> findAll(@RequestParam(defaultValue = "20")int limit,
                                   @RequestParam(defaultValue = "0")int offset,
                                   @RequestParam(required = false) String search,
                                   @SortDefault Sort sort) {
        Specification<Seller> spec = (search != null) ? SearchSpecification.parse(search) : Specification.where(null);
        return sellerService.findAll(new PageSearch.Builder<Seller>()
                .limit(limit)
                .offset(offset)
                .search(spec)
                .sort(sort).build());
    }
    @JsonView(Seller.Views.FULL.class)
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<EntityModel<Seller>> getSeller(@PathVariable long id) {
        Seller seller = sellerService.findById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("no seller with id %d", id)));
        return new ResponseEntity<>(assembler.toModel(seller), HttpStatus.OK);
    }
    @JsonView(Seller.Views.LIGHT.class)
    @GetMapping(value = "/{id}/articles",   produces = "application/json")
    public Page<Article> findAllBySeller(
            @RequestParam(defaultValue = "20")int limit,
            @RequestParam(defaultValue = "0")int offset,
            @PathVariable long id,
            @SortDefault Sort sort) {

        Specification<Article> spec = SearchSpecification.parse("seller.id="+id);

        return articleService.findAll(new PageSearch.Builder<Article>()
                .limit(limit)
                .offset(offset)
                .search(spec)
                .sort(sort).build());
    }
    @JsonView(Seller.Views.LIGHT.class)
    @PostMapping(value = "", produces = "application/json")
    public ResponseEntity<EntityModel<Seller>> create(@RequestBody Seller seller) {
        if (seller.getId() != null) {
            throw new IllegalArgumentException("cannot create a seller and specify the ID");
        }
        sellerService.create(seller);

        return new ResponseEntity<>(assembler.toModel(seller), HttpStatus.CREATED);
    }
    @JsonView(Seller.Views.ID.class)
    @PutMapping(value = "", produces = "application/json")
    public ResponseEntity<EntityModel<Seller>> update(@RequestBody() Seller seller) {
        if (seller.getId() == null) {
            throw new IllegalArgumentException("did not specify the ID of seller to update");
        }
        sellerService.update(seller);

        return new ResponseEntity<>(assembler.toModel(seller), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public void delete(@PathVariable long id) {
        sellerService.deleteById(id);
    }
}
@Component
class SellerRepresentationModelAssembler implements RepresentationModelAssembler<Seller, EntityModel<Seller>> {
    public EntityModel<Seller> toModel(Seller seller) {
        Link selfLink = linkTo(methodOn(SellerApi.class).getSeller(seller.getId())).withSelfRel();
        return EntityModel.of(seller, selfLink);
    }
}

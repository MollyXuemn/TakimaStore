package io.takima.master3.store.seller.presentation;

import io.takima.master3.store.article.models.Article;
import io.takima.master3.store.article.service.ArticleService;
import io.takima.master3.store.core.pagination.PageSearch;
import io.takima.master3.store.core.pagination.SearchSpecification;
import io.takima.master3.store.seller.models.Seller;
import io.takima.master3.store.seller.service.SellerService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/sellers")
@AllArgsConstructor
public class SellerApi {
    private final SellerService sellerService;
    private final ArticleService articleService;
    // TODO would need a refactor to be more RESTful. This is the topic of the REST milestone.
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
    // TODO would need a refactor to be more RESTful. This is the topic of the REST milestone.
    @GetMapping(value = "/{id}", produces = "application/json")
    public Seller getSeller(@PathVariable long id) {
        return sellerService.findById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("no seller with id %d", id)));
    }
    @GetMapping(value = "/{id}/articles", produces = "application/json")
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
    // TODO would need a refactor to be more RESTful. This is the topic of the REST milestone.
    @PostMapping(value = "", produces = "application/json")
    public Seller create(@RequestBody Seller seller) {
        if (seller.getId() != null) {
            throw new IllegalArgumentException("cannot create a seller and specify the ID");
        }
        sellerService.create(seller);

        return seller;
    }

    // TODO would need a refactor to be more RESTful. This is the topic of the REST milestone.
    @PutMapping(value = "", produces = "application/json")
    public Seller update(@RequestBody() Seller seller) {
        if (seller.getId() == null) {
            throw new IllegalArgumentException("did not specify the ID of seller to update");
        }
        sellerService.update(seller);

        return seller;
    }

    // TODO would need a refactor to be more RESTful. This is the topic of the REST milestone.
    @DeleteMapping(value = "/{id}", produces = "application/json")
    public void delete(@PathVariable long id) {
        sellerService.deleteById(id);
    }
}

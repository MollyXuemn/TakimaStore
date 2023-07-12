package io.takima.master3.store.seller.presentation;

import com.fasterxml.jackson.annotation.JsonView;
import io.takima.master3.store.article.models.Article;
import io.takima.master3.store.article.service.ArticleService;
import io.takima.master3.store.core.converters.models.PageableAsQueryParam;
import io.takima.master3.store.core.pagination.PageSearch;
import io.takima.master3.store.core.pagination.PageSearchDefault;
import io.takima.master3.store.seller.models.Seller;
import io.takima.master3.store.seller.service.SellerService;
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
@RequestMapping(value = "/api/sellers", produces = MediaType.APPLICATION_JSON_VALUE)
public class SellerApi {

    private final SellerService sellerService;
    private final ArticleService articleService;

    @Autowired
    SellerApi(SellerService sellerService, ArticleService articleService) {
        this.sellerService = sellerService;
        this.articleService = articleService;
    }

    @GetMapping
    @JsonView(Seller.Views.FULL.class)
    @PageableAsQueryParam
    public Page<Seller> getAll(@PageSearchDefault PageSearch<Seller> pageSearch) {
        return sellerService.findAll(pageSearch);
    }

    @GetMapping("{id}")
    @JsonView(Seller.Views.FULL.class)
    public Seller getOne(@PathVariable long id) {
        return sellerService.findById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("no seller with id %d", id)));
    }

    @GetMapping("{id}/articles")
    @JsonView(Seller.Views.LIGHT.class)
    public Page<Article> getAllArticles(@PathVariable long id) {
        return articleService.findBySeller(sellerService.findById(id).orElseThrow(() -> new NoSuchElementException(String.format("no seller with id %d", id))));
    }

    @PostMapping
    @JsonView(Seller.Views.ID.class)
    public ResponseEntity<EntityModel<Seller>> createOne(@RequestBody() Seller seller) {
        if (seller.getId() != null) {
            throw new IllegalArgumentException("cannot create a seller and specify the ID");
        }

        sellerService.create(seller);

        return new ResponseEntity<>(new SellerRepresentationModelAssembler().toModel(seller), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<EntityModel<Seller>> updateOne(@RequestBody() Seller seller) {
        if (seller.getId() == null) {
            throw new IllegalArgumentException("did not specify the ID of seller to update");
        }

        sellerService.update(seller);

        return new ResponseEntity<>(new SellerRepresentationModelAssembler().toModel(seller), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public void deleteOne(@PathVariable long id) {
        sellerService.deleteById(id);
    }

}


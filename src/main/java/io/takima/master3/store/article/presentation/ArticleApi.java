package io.takima.master3.store.article.presentation;

import com.fasterxml.jackson.annotation.JsonView;
import io.takima.master3.store.article.models.Article;
import io.takima.master3.store.article.service.ArticleService;
import io.takima.master3.store.cart.presentation.CartApi;
import io.takima.master3.store.core.pagination.PageSearch;
import io.takima.master3.store.core.pagination.SearchSpecification;
import io.takima.master3.store.customer.models.Customer;
import io.takima.master3.store.customer.presentation.CustomerApi;
import io.takima.master3.store.seller.models.Seller;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.data.web.SortDefault;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.NoSuchElementException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api/articles", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class ArticleApi {
    private final ArticleService articleService;
    private final ArticleRepresentationModelAssembler assembler;

    private final PagedResourcesAssembler<Article> pageAssembler;

    @JsonView(Article.Views.LIGHT.class)
    @GetMapping(value = "", produces = "application/json")
    public PagedModel<EntityModel<Article>> findAll(
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(required = false) String search,
            @SortDefault Sort sort) {

        Specification<Article> spec = (search != null) ? SearchSpecification.parse(search) : Specification.where(null);
        return pageAssembler.toModel(articleService.findAll(new PageSearch.Builder<Article>()
                .limit( limit)
                .offset( offset)
                .search(spec)
                .sort(sort).build()));
    }

    @JsonView(Article.Views.FULL.class)
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<EntityModel<Article>> getOne(@PathVariable long id) {
        Article article = articleService.findById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("no article with id %d", id)));
        return new ResponseEntity<>(assembler.toModel(article), HttpStatus.OK);
    }
    @JsonView(Article.Views.ID.class)
    @PostMapping(value = "", produces = "application/json")
    public ResponseEntity<EntityModel<Article>> create(@RequestBody() Article article) {
        if (article.getId() != null) {
            throw new IllegalArgumentException("cannot create an article and specify the ID");
        }
        articleService.create(article);
        //URI uri = linkTo(methodOn(ArticleApi.class).getOne(article.getId())).toUri();
        return new ResponseEntity<>(assembler.toModel(article), HttpStatus.CREATED);

    }

    @PutMapping(value = "", produces = "application/json")
    public ResponseEntity<EntityModel<Article>> update(@RequestBody() Article article) {
        if (article.getId() == null) {
            throw new IllegalArgumentException("did not specify the ID of article to update");
        }
        articleService.update(article);
        return new ResponseEntity<>(assembler.toModel(article), HttpStatus.CREATED);
    }
    @DeleteMapping(value = "/{id}", produces = "application/json")
    public void delete(@PathVariable long id) {
        articleService.deleteById(id);
    }
}
@Component
class ArticleRepresentationModelAssembler implements RepresentationModelAssembler<Article, EntityModel<Article>> {
    public EntityModel<Article> toModel(Article article) {
        Link selfLink = linkTo(methodOn(ArticleApi.class).getOne(article.getId())).withSelfRel();
        return EntityModel.of(article, selfLink);
    }
}


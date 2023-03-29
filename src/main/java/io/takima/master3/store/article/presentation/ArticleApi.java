package io.takima.master3.store.article.presentation;

import com.fasterxml.jackson.annotation.JsonView;
import io.takima.master3.store.article.models.Article;
import io.takima.master3.store.article.service.ArticleService;
import io.takima.master3.store.core.pagination.PageSearch;
import io.takima.master3.store.core.pagination.SearchSpecification;
import io.takima.master3.store.customer.models.Customer;
import io.takima.master3.store.seller.models.Seller;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping(value = "/api/articles", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class ArticleApi {
    private final ArticleService articleService;
    @JsonView(Article.Views.LIGHT.class)
    @GetMapping(value = "", produces = "application/json")
    public Page<Article> findAll(
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(required = false) String search,
            @SortDefault Sort sort) {

        Specification<Article> spec = (search != null) ? SearchSpecification.parse(search) : Specification.where(null);
        return articleService.findAll(new PageSearch.Builder<Article>()
                .limit( limit)
                .offset( offset)
                .search(spec)
                .sort(sort).build());
    }

    @JsonView(Article.Views.FULL.class)
    @GetMapping(value = "/{id}", produces = "application/json")
    public Article getOne(@PathVariable long id) {
        return articleService.findById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("no article with id %d", id)));
    }
    @JsonView(Article.Views.ID.class)
    @PostMapping(value = "", produces = "application/json")
    public ResponseEntity<Article> create(@RequestBody() Article article) {
        if (article.getId() != null) {
            throw new IllegalArgumentException("cannot create an article and specify the ID");
        }

        articleService.create(article);
        return new ResponseEntity<>(article, HttpStatus.CREATED);
    }

    @PutMapping(value = "", produces = "application/json")
    public Article update(@RequestBody() Article article) {
        if (article.getId() == null) {
            throw new IllegalArgumentException("did not specify the ID of article to update");
        }
        articleService.update(article);

        return article;
    }
    @DeleteMapping(value = "/{id}", produces = "application/json")
    public void delete(@PathVariable long id) {
        articleService.deleteById(id);
    }

}

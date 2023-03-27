package io.takima.master3.store.article.presentation;

import io.takima.master3.store.article.models.Article;
import io.takima.master3.store.article.service.ArticleService;
import io.takima.master3.store.core.pagination.PageSearch;
import io.takima.master3.store.core.pagination.SearchSpecification;
import io.takima.master3.store.seller.models.Seller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.SortDefault;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping(value = "/api/articles", produces = MediaType.APPLICATION_JSON_VALUE)
public class ArticleApi {
    private final ArticleService articleService;
    @Autowired
    public ArticleApi(ArticleService articleService) {
        this.articleService = articleService;
    }

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


    @GetMapping(value = "/{id}", produces = "application/json")
    public Article getOne(@PathVariable long id) {
        return articleService.findById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("no seller with id %d", id)));
    }


    // TODO would need a refactor to be more RESTful. This is the topic of the REST milestone.
    @PostMapping(value = "", produces = "application/json")
    public Article create(@RequestBody() Article article) {
        if (article.getId() != null) {
            throw new IllegalArgumentException("cannot create a customer and specify the ID");
        }

        articleService.create(article);
        return article;
    }

    // TODO would need a refactor to be more RESTful. This is the topic of the REST milestone.
    @PutMapping(value = "", produces = "application/json")
    public Article update(@RequestBody() Article article) {
        if (article.getId() == null) {
            throw new IllegalArgumentException("did not specify the ID of article to update");
        }

        articleService.update(article);

        return article;
    }

    // TODO would need a refactor to be more RESTful. This is the topic of the REST milestone.
    @DeleteMapping(value = "/{id}", produces = "application/json")
    public void delete(@PathVariable long id) {
        articleService.deleteById(id);
    }

}

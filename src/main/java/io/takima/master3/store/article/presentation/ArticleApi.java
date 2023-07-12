package io.takima.master3.store.article.presentation;

import com.fasterxml.jackson.annotation.JsonView;
import io.takima.master3.store.article.models.Article;
import io.takima.master3.store.article.service.ArticleService;
import io.takima.master3.store.core.converters.models.PageableAsQueryParam;
import io.takima.master3.store.core.pagination.PageSearch;
import io.takima.master3.store.core.pagination.PageSearchDefault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/articles", produces = MediaType.APPLICATION_JSON_VALUE)
public class ArticleApi {

    private final ArticleService articleService;


    @Autowired
    ArticleApi(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    @JsonView(Article.Views.LIGHT.class)
    public Page<Article> getAll(@PageSearchDefault PageSearch<Article> pageSearch) {
        return articleService.findAll(pageSearch);
    }

    @GetMapping("{id}")
    @JsonView(Article.Views.FULL.class)
    public Article getOne(@PathVariable long id) {
        return articleService.findById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("no article with id %d", id)));
    }

    @PostMapping
    @JsonView(Article.Views.ID.class)
    public ResponseEntity<EntityModel<Article>> createOne(@RequestBody() Article article) {
        if (article.getId() != null) {
            throw new IllegalArgumentException("cannot create an article and specify the ID");
        }

        articleService.create(article);

        return new ResponseEntity<>(new ArticleRepresentationModelAssembler().toModel(article), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<EntityModel<Article>> updateOne(@RequestBody() Article article) {
        if (article.getId() == null) {
            throw new IllegalArgumentException("did not specify the ID of article to update");
        }

        articleService.update(article);

        return new ResponseEntity<>(new ArticleRepresentationModelAssembler().toModel(article), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public void deleteOne(@PathVariable long id) {
        articleService.delete(id);
    }
}


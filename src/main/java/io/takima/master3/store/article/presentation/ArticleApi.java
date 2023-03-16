package io.takima.master3.store.article.presentation;

import io.takima.master3.store.article.models.Article;
import io.takima.master3.store.article.service.ArticleService;
import io.takima.master3.store.core.pagination.PageResponse;
import io.takima.master3.store.core.pagination.PageSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.SortDefault;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class ArticleApi {
    private final ArticleService articleService;
    @Autowired
    public ArticleApi(ArticleService articleService) {
        this.articleService = articleService;
    }

    @ResponseBody
    @GetMapping(value = "/getAllArticles",  produces = "application/json")
    public Page<Article> getAllArticles(
            @RequestParam(required = false,defaultValue = "20") int offset,
            @RequestParam(required = false,defaultValue = "10") int limit,
            @RequestParam(required = false,defaultValue = "") Specification search,
            @SortDefault Sort sort
    ) {

        return articleService.findAll(new PageSearch
                .Builder<Article>()
                .offset(offset)
                .limit(limit)
                .search(search)
                .sort(sort)
                .build());

    }
}

package io.takima.master3.store.article.presentation;

import io.takima.master3.store.article.models.Article;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class ArticleApi {
    @ResponseBody
    @GetMapping(value = "/getAllArticles",  produces = "application/json")
    public List<Article> getAllArticles() {
        // TODO call ArticleService to return all articles
    }
}

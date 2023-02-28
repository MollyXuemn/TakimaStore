package io.takima.master3.store.core.presentation;

import io.takima.master3.store.article.models.Article;
import io.takima.master3.store.seller.models.Seller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api")     // execute this controller when `/api` is reached
public class CliApi {
    private CliLister cli;

    @Autowired
    public CliApi(CliLister cli) {
        this.cli = cli;
    }

    @ResponseBody
    @GetMapping(value = "/cli",  produces = "application/json")// execute this method when `/api/cli` is reached
    public Map<Seller, List<Article>> list() {
        return cli.list();
    }
}

package io.takima.master3.store;

import io.takima.master3.store.article.service.ArticleService;
import io.takima.master3.store.seller.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.EventListener;


@SpringBootApplication
public class MaStoreApplication {
    private final ArticleService articleService ;
    private final SellerService sellerService;

    @Autowired
    public MaStoreApplication(SellerService sellerService, ArticleService articleService) {
        this.sellerService = sellerService;
        this.articleService = articleService;
        // Put your old main code here
        //new MainApplication(this.articleService,this.sellerService).run();
    }

    public static void main(String[] args) {
        SpringApplication.run(MaStoreApplication.class, args);
    }
}

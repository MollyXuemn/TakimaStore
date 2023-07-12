package io.takima.master3.store.article.presentation;

import io.takima.master3.store.article.models.Article;
import org.jetbrains.annotations.NotNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ArticleRepresentationModelAssembler implements RepresentationModelAssembler<Article, EntityModel<Article>> {
    public @NotNull EntityModel<Article> toModel(Article article) {
        Link selfLink = linkTo(methodOn(ArticleApi.class).getOne(article.getId())).withSelfRel();
        return EntityModel.of(article, selfLink);
    }
}

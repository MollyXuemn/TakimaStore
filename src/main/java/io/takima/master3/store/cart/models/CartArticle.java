package io.takima.master3.store.cart.models;

import io.takima.master3.store.article.models.Article;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class CartArticle {

    public CartArticle() {

    }

    @Embeddable
    static class CartArticlePk implements Serializable {
        @Column(name = "cart_id")
        Long cartId;
        @Column(name = "article_id")
        Long articleId;

        @Override
        public String toString() {
            return "(cartId=" + cartId + ", articleId=" + articleId + ")";
        }

        public boolean equals(Object o) {
            if (o == this)
                return true;
            if (!(o instanceof CartArticlePk))
                return false;
            final CartArticlePk other = (CartArticlePk) o;
            if (!other.canEqual((Object) this))
                return false;
            final Object this$cartId = this.cartId;
            final Object other$cartId = other.cartId;
            if (this$cartId == null ? other$cartId != null : !this$cartId.equals(other$cartId))
                return false;
            final Object this$articleId = this.articleId;
            final Object other$articleId = other.articleId;
            if (this$articleId == null ? other$articleId != null : !this$articleId.equals(other$articleId))
                return false;
            return true;
        }

        public int hashCode() {
            final int PRIME = 59;
            int result = 1;
            final Object $cartId = this.cartId;
            result = result * PRIME + ($cartId == null ? 43 : $cartId.hashCode());
            final Object $articleId = this.articleId;
            result = result * PRIME + ($articleId == null ? 43 : $articleId.hashCode());
            return result;
        }

        protected boolean canEqual(Object other) {
            return other instanceof CartArticlePk;
        }
    }

    @EmbeddedId
    private final CartArticlePk id = new CartArticlePk();

    @ManyToOne
    @MapsId("cart_id")
    private Cart cart;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @MapsId("article_id")
    private Article article;

    @Column(name = "qty")
    private int quantity;

    public CartArticle(Cart cart, Article article, Integer quantity) {
        this.cart = cart;
        this.article = article;
        this.quantity = quantity;
        this.id.cartId = cart.getId();
        this.id.articleId = article.getId();
    }

    public CartArticlePk getId() {
        return id;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

package io.takima.master3.store.cart.presentation;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.takima.master3.store.article.models.Article;

@JsonIgnoreProperties({"availableQuantity", "seller"})
public class CartArticleDTO extends Article {

    private int quantity;

    public CartArticleDTO(Article article) {
        super(article);
    }

    static CartArticleDTO fromModel(Article article) {
        return new CartArticleDTO(article);
    }
    public int getQuantity() {
        return this.quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public String toString() {
        return "CartArticleDTO(quantity=" + this.getQuantity() + ")";
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof CartArticleDTO)) return false;
        final CartArticleDTO other = (CartArticleDTO) o;
        if (!other.canEqual((Object) this)) return false;
        if (!super.equals(o)) return false;
        if (this.getQuantity() != other.getQuantity()) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + super.hashCode();
        result = result * PRIME + this.getQuantity();
        return result;
    }

    protected boolean canEqual(Object other) {
        return other instanceof CartArticleDTO;
    }
}

package io.takima.master3.store.mapper  ;

import io.takima.master3.store.article.models.Article;
import io.takima.master3.store.core.models.Price;
import io.takima.master3.store.seller.models.Seller;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ArticleMapper implements ResultSetMapper<Article> {
    @Override
    public Article map(ResultSet resultSet) throws SQLException {
        Price price = new Price(resultSet.getDouble("price"),resultSet.getString("currency"));
        return Article.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .description(resultSet.getString("description"))
                .ref(resultSet.getString("ref"))
                .image(resultSet.getString("image"))
                .availableQuantity(resultSet.getInt("available_quantity"))
                .price(price)
                .seller(Seller.builder().id(resultSet.getLong("seller_id")).build())
                .build();
    }
}
package io.takima.master3.store.mapper;
import io.takima.master3.store.seller.models.Seller;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class SellerMapper implements ResultSetMapper<Seller>  {
    public Seller map(ResultSet resultSet) throws SQLException {
        return Seller.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .street(resultSet.getString("street"))
                .city(resultSet.getString("city"))
                .zipcode(resultSet.getString("zipcode"))
                .country(resultSet.getString("country"))
                .iban(resultSet.getString("iban"))
                .build();
    }
}

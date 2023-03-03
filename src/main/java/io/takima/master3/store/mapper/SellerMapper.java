package io.takima.master3.store.mapper;
import io.takima.master3.store.core.models.Address;
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
                .address((Address) resultSet.getObject("address"))
                .iban(resultSet.getString("iban"))
                .build();
    }
}

package io.takima.master3.store.mapper;
import io.takima.master3.store.domain.Seller;
import java.sql.ResultSet;
import java.sql.SQLException;
public enum SellerMapper implements ResultSetMapper<Seller>  {
    INSTANCE;
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

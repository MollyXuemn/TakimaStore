package io.takima.master3.store.seller.persistence;
import io.takima.master3.store.domain.Seller;
import io.takima.master3.store.ConnectionManager;
import io.takima.master3.store.mapper.ResultSetMapper;
import io.takima.master3.store.mapper.SellerMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.*;


public enum JdbcSellerDao implements SellerDao {
    INSTANCE;
    ResultSetMapper<Seller> sellerMapper = SellerMapper.INSTANCE ;
    List<Seller> sellers = new ArrayList<>();

    public void SellerDao(ResultSetMapper<Seller> sellerMapper) {
        this.sellerMapper = SellerMapper.INSTANCE;
    }

    public List<Seller> findAll() {
        try (
                var conn = ConnectionManager.INSTANCE.getConnection();
                var ps = conn.prepareStatement("SELECT * from seller")) {
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                sellers.add(sellerMapper.map(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return sellers;
    }
    public List<Seller> findByName(String name){

        try (
                var conn = ConnectionManager.INSTANCE.getConnection();
                var ps = conn.prepareStatement("SELECT * FROM seller WHERE name= ?")) {
            ps.setString(1,"name");
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    sellers.add(sellerMapper.map(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Impossible to insert site " +
                    name, e);
        }
        return sellers;
    }


    public Optional<Seller> findById(long id) {
        try (
                var conn = ConnectionManager.INSTANCE.getConnection();
                var ps = conn.prepareStatement("SELECT * FROM seller WHERE id = ?")) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return Optional.of(sellerMapper.map(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }
    public void update(Seller seller){
        try (
                var conn = ConnectionManager.INSTANCE.getConnection();
                var ps = conn.prepareStatement(
                        "UPDATE seller set id, name, street, city, zipcode, country, iban WHERE id = ?")) {
            ps.setLong(1, Long.parseLong("id"));
            ps.setString(2,"name");
            ps.setString(4,"street");
            ps.setString(5,"city");
            ps.setString(6, "zipcode");
            ps.setString(7, "country");
            ps.setString(8,"iban");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sellers.add(sellerMapper.map(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    };
    public void create(Seller seller){
        try (
                var conn = ConnectionManager.INSTANCE.getConnection();
                var ps = conn.prepareStatement(
                        "INSERT INTO seller (id, name, street, city, zipcode, country, iban) VALUES (?,?,?,?,?,?,?) ")) {
            ps.setLong(1, Long.parseLong("id"));
            ps.setString(2,"name");
            ps.setString(4,"street");
            ps.setString(5,"city");
            ps.setString(6, "zipcode");
            ps.setString(7, "country");
            ps.setString(8,"iban");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sellers.add(sellerMapper.map(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    };

    public void delete(long id) throws SQLException {
        try (
                var conn = ConnectionManager.INSTANCE.getConnection();
                var ps = conn.prepareStatement(
                        "DELETE FROM seller WHERE id = ?")) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sellers.add(sellerMapper.map(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    };
}

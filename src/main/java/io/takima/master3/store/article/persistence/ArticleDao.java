package io.takima.master3.store.article.persistence;

import io.takima.master3.store.ConnectionManager;
import io.takima.master3.store.mapper.ArticleMapper;
import io.takima.master3.store.mapper.ResultSetMapper;
import io.takima.master3.store.domain.Article;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


public interface ArticleDao {
    ResultSetMapper<Article> articleMapper = ArticleMapper.INSTANCE;
    List<Article> articles = new ArrayList<>();

    public default List<Article> findAll(){
        try (
                var conn = ConnectionManager.INSTANCE.getConnection();
                var ps = conn.prepareStatement("SELECT * from article")) {
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                articles.add(articleMapper.map(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return articles;
    };
    public default List<Article> findByName(String name){

        try (
                var conn = ConnectionManager.INSTANCE.getConnection();
                var ps = conn.prepareStatement("SELECT * FROM article WHERE name= ?")) {
            ps.setString(1,"name");
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    articles.add(articleMapper.map(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Impossible to insert site " +
                    name, e);
        }
        return articles;
    }
    public default Optional<Article> findById(long id){
        try (
                var conn = ConnectionManager.INSTANCE.getConnection();
                var ps = conn.prepareStatement("SELECT * FROM article WHERE id = ?")) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
/*                    int articleId = rs.getInt("id");
                    String name = rs.getString("name");
                    String ref = rs.getString("name");
                    String description = rs.getString("name");
                    String image= rs.getString("name");
                    int availableQuantity = rs.getInt("available_quantity");*/
                return Optional.of(articleMapper.map(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    };
    public default List<Article> findBySellerId(long sellerId) {
        try (
                var conn = ConnectionManager.INSTANCE.getConnection();
                var ps = conn.prepareStatement("SELECT * FROM article WHERE seller_id = ?")) {
                ps.setLong(1, sellerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    articles.add(articleMapper.map(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return articles;
    };


    public default void update(Article article){
        try (
                var conn = ConnectionManager.INSTANCE.getConnection();
                var ps = conn.prepareStatement(
                        "UPDATE article set seller_id, name, description, ref, image, available_quantity, price, currency WHERE id = ?")) {
            ps.setLong(1, Long.parseLong("id"));
            ps.setString(2,"name");
            ps.setString(3,"description");
            ps.setString(4,"ref");
            ps.setString(5,"image");
            ps.setInt(6, Integer.parseInt("available_quantity"));
            ps.setDouble(7, Double.parseDouble("price"));
            ps.setString(8,"currency");
            ps.setLong(9, Long.parseLong("seller_id"));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                articles.add(articleMapper.map(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    };

    public default void create(Article article){
        try (
                var conn = ConnectionManager.INSTANCE.getConnection();
                var ps = conn.prepareStatement(
                        "INSERT INTO article (id, seller_id, name, description, ref, image, available_quantity, price, currency) VALUES (?,?,?,?,?,?,?,?,?) ")) {
            ps.setLong(1, Long.parseLong("id"));
            ps.setString(2,"name");
            ps.setString(3,"description");
            ps.setString(4,"ref");
            ps.setString(5,"image");
            ps.setInt(6, Integer.parseInt("available_quantity"));
            ps.setDouble(7, Double.parseDouble("price"));
            ps.setString(8,"currency");
            ps.setLong(9, Long.parseLong("seller_id"));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                articles.add(articleMapper.map(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    };

    public default void delete(long id) throws SQLException {
        try (
                var conn = ConnectionManager.INSTANCE.getConnection();
                var ps = conn.prepareStatement(
                        "DELETE  FROM article WHERE id = ?")) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                articles.add(articleMapper.map(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    };

}

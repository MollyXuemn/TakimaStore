package io.takima.master3.store.article.persistence;
import io.takima.master3.store.ConnectionManager;
import io.takima.master3.store.mapper.ArticleMapper;
import io.takima.master3.store.mapper.ResultSetMapper;
import io.takima.master3.store.domain.Article;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


public enum JdbcArticleDao implements ArticleDao {
    INSTANCE;
    ResultSetMapper<Article> articleMapper = ArticleMapper.INSTANCE;

    void JdbcArticleDao() {
        this.articleMapper = ArticleMapper.INSTANCE;
    }

    public List<Article> findAll(){
        List<Article> articles = new ArrayList<>();
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
    public List<Article> findByName(String name){
        List<Article> articles = new ArrayList<>();
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
    public Optional<Article> findById(long id){
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

    public List<Article> findBySellerId(long sellerId) {
        List<Article> articles = new ArrayList<>();
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

    public void update(Article article){
        List<Article> articles = new ArrayList<>();
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
    public void create(Article article){
        List<Article> articles = new ArrayList<>();
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

    public void delete(long id) throws SQLException {
        List<Article> articles = new ArrayList<>();
        try (
                var conn = ConnectionManager.INSTANCE.getConnection();
                var ps = conn.prepareStatement(
                        "DELETE FROM article WHERE id = ?")) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                articles.add(articleMapper.map(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    };

};

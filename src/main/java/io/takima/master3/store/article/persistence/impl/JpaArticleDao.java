package io.takima.master3.store.article.persistence.impl;
import io.takima.master3.store.article.models.Article;
import io.takima.master3.store.article.persistence.ArticleDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import java.sql.SQLException;
import java.util.*;


@Repository
public class JpaArticleDao implements ArticleDao {
    @PersistenceContext
    private EntityManager em;

    public List findAll(int offset,int limit){
        return em.createQuery("select a FROM Article a", Article.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }
    public List<Article> findByName(String name){
        return em.createQuery("SELECT a FROM Article a WHERE a.product.name = :name", Article.class)
                .setParameter("name",name)
                .getResultList();
    }
    public Optional<Article> findById(long id){
        return Optional.ofNullable(em.find(Article.class, id));
    };

    public List<Article> findBySellerId(long sellerId) {
        return em.createQuery("SELECT a FROM Article a JOIN a.seller WHERE a.seller.id = :id", Article.class)
                .setParameter("id",sellerId)
                .getResultList();

    };

    public Article update(Article article){
        Long articleId = article.getId();
        Optional<Article> optionalArticle = findById(articleId);
        optionalArticle.ifPresentOrElse(c -> em.merge(article), //the managed instance that the state was merged to
                () -> { throw new NoSuchElementException(String.format("No customer with id: %d.", articleId)); });
        return article;

    };

    public Article create(Article article){
        em.persist(article);
        return article;
    };
    @Transactional
    public void delete(long id) throws SQLException {
        em.remove(findById(id).orElseThrow(() ->
                new NoSuchElementException(String.format("no article with id %d", id))));
    };

};

package io.takima.master3.store.article.persistence.impl;
import io.takima.master3.store.article.models.Article;
import io.takima.master3.store.article.persistence.ArticleDao;
import io.takima.master3.store.core.pagination.PageResponse;
import io.takima.master3.store.core.pagination.PageSearch;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import java.sql.SQLException;
import java.util.*;


@Repository
public class JpaArticleDao implements ArticleDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public PageResponse<Article> findAll(PageSearch pageSearch) {
        TypedQuery query = em.createQuery("SELECT a FROM Article a WHERE LOWER(a.product.name) LIKE LOWER(CONCAT('%', :name, '%'))", Article.class);
        var countQuery = em.createQuery("SELECT COUNT(a) FROM Article a WHERE a.product.name LIKE :name", Integer.class);


        query.setParameter("name", pageSearch.getSearch());
        countQuery.setParameter("name", pageSearch.getSearch());
        query.setFirstResult((int) pageSearch.getOffset());
        query.setMaxResults(pageSearch.getLimit());
        List<Article> content = query.getResultList();
        var totalElements = countQuery.getFirstResult();
        PageResponse<Article> pageResponse = new PageResponse<Article>(pageSearch, content,totalElements);
        return pageResponse;
    }

    public List<Article> findByName(String name){
        return em.createQuery("SELECT a FROM Article a WHERE a.product.name = :name", Article.class)
                .setParameter("name",name)
                .getResultList();
    }
    public Optional<Article> findById(long id){
        return Optional.ofNullable(em.find(Article.class, id));
    };

    public PageResponse<Article> findBySellerId(long sellerId) {
        TypedQuery query = em.createQuery("SELECT a FROM Article a JOIN a.seller WHERE a.seller.id = :id", Article.class);
        query.setParameter("id",sellerId);
        List<Article> content = query.getResultList();
        return new PageResponse(content);


    };
    public long count(PageSearch pageSearch){
        final String jpql = "SELECT a FROM Article a WHERE LOWER(a.product.name) LIKE LOWER(CONCAT('%', :name, '%')) ORDER BY a.product.name ";
        TypedQuery<Article> query = em.createQuery(jpql, Article.class);
        query.setParameter("name", pageSearch.getSearch());

        List<Article> content = query.getResultList();
        PageResponse<Article> pageResponse = new PageResponse<>(content);
        pageResponse.setTotalElements(content.size());

        return pageResponse.getTotalElements();

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

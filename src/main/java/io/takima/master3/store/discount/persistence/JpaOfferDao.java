package io.takima.master3.store.discount.persistence;

import io.takima.master3.store.article.models.Article;
import io.takima.master3.store.discount.models.Offer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Repository
@Transactional(readOnly = true)
public interface JpaOfferDao extends OfferDao {
    @Override
    @Query("SELECT o FROM Offer o WHERE o.code = :code")
    Optional<Offer> findByCode(@Param("code") String code);

    @Override
    @Query("SELECT o FROM Offer o " +
            "WHERE o.startDate <= :date AND o.expireDate >= :date " +
            "AND o.code IS NULL " +
            "AND o.articles IS EMPTY"
    )
    Set<Offer> findOffersWithoutCode(@Param("date") LocalDateTime date);

    @Override
    @Query("SELECT o FROM Offer o " +
            "WHERE o.startDate >= :startDate AND o.expireDate <= :expireDate " +
            "AND o.code IS NULL " +
            "AND o.articles IS EMPTY"
    )
    Set<Offer> findOffersWithoutCode(@Param("startDate") LocalDateTime startDate, @Param("expireDate") LocalDateTime expireDate);

    @Override
    @Query("SELECT DISTINCT o FROM Offer o JOIN FETCH o.articles a " +
            "WHERE o.startDate <= :date AND o.expireDate >= :date " +
            "AND o.code IS NULL " +
            "AND a IN :articles")
    Set<Offer> findOffersWithoutCode(@Param("date") LocalDateTime startDate, @Param("articles") Set<? extends Article> articles);

    @Query("SELECT DISTINCT o FROM Offer o JOIN FETCH o.articles a " +
            "WHERE o.startDate >= :startDate AND o.expireDate <= :expireDate " +
            "AND o.code IS NULL " +
            "AND a IN :articles")
    Set<Offer> findOffersWithoutCode(@Param("startDate") LocalDateTime startDate, @Param("expireDate") LocalDateTime expireDate,
                                     @Param("articles") Set<? extends Article> articles);
}

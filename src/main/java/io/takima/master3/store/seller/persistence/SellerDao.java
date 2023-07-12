package io.takima.master3.store.seller.persistence;

import io.takima.master3.store.core.pagination.PageSearch;
import io.takima.master3.store.seller.models.Seller;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface SellerDao {
    /**
     * Find a seller by its ID
     *
     * @param id the id of seller to findById
     * @return the seller with the searched id.
     */
    Optional<Seller> findById(long id);

    /**
     * Get a page of Seller, searched by the given page-search.
     * @param pageSearch the page-search. Searches for a given specification in a given order with a given limit and a given offset.
     * @return the page of all Sellers that matches the given search
     */
    Page<Seller> findAll(PageSearch<Seller> pageSearch);

    /**
     * Save a seller
     * @param seller the seller to save
     */
    Seller save(Seller seller);

    /**
     * Delete a seller by id
     * @param id the id of seller to delete
     */
    void deleteById(long id);

}

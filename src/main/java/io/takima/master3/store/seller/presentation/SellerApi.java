package io.takima.master3.store.seller.presentation;

import io.takima.master3.store.core.pagination.PageSearch;
import io.takima.master3.store.core.pagination.SearchSpecification;
import io.takima.master3.store.seller.models.Seller;
import io.takima.master3.store.seller.service.SellerService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Controller
@RequestMapping("/api")
@AllArgsConstructor
public class SellerApi {
    private final SellerService sellerService;

    // TODO would need a refactor to be more RESTful. This is the topic of the REST milestone.
    @ResponseBody
    @GetMapping(value = "/getAllSellers", produces = "application/json")
    public Page<Seller> findAll(@RequestParam(defaultValue = "20")int limit,
                                   @RequestParam(defaultValue = "0")int offset,
                                   @RequestParam(required = false) String search,
                                   @SortDefault Sort sort) {
        Specification<Seller> spec = (search != null) ? SearchSpecification.parse(search) : Specification.where(null);
        return sellerService.findAll(new PageSearch.Builder<Seller>()
                .limit(limit)
                .offset(offset)
                .search(spec)
                .sort(sort).build());
    }
    // TODO would need a refactor to be more RESTful. This is the topic of the REST milestone.
    @ResponseBody
    @GetMapping(value = "/getSeller", produces = "application/json")
    public Seller getSeller(@RequestParam() long id) {
        return sellerService.findById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("no seller with id %d", id)));
    }

    // TODO would need a refactor to be more RESTful. This is the topic of the REST milestone.
    @ResponseBody
    @PostMapping(value = "/createSeller", produces = "application/json")
    public Seller create(@RequestBody Seller seller) {
        if (seller.getId() != null) {
            throw new IllegalArgumentException("cannot create a seller and specify the ID");
        }
        sellerService.create(seller);

        return seller;
    }

    // TODO would need a refactor to be more RESTful. This is the topic of the REST milestone.
    @ResponseBody
    @PostMapping(value = "/updateSeller", produces = "application/json")
    public Seller update(@RequestBody() Seller seller) {
        if (seller.getId() == null) {
            throw new IllegalArgumentException("did not specify the ID of seller to update");
        }
        sellerService.update(seller);

        return seller;
    }

    // TODO would need a refactor to be more RESTful. This is the topic of the REST milestone.
    @ResponseBody
    @GetMapping(value = "/deleteSeller", produces = "application/json")
    public void delete(@RequestParam() long id) {
        sellerService.deleteById(id);
    }
}

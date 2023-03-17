package io.takima.master3.store.customer;

import io.takima.master3.store.customer.models.Customer;
import io.takima.master3.store.customer.models.Gender;
import io.takima.master3.store.customer.persistence.CustomerDao;
import io.takima.master3.store.core.models.Address;
import io.takima.master3.store.core.models.Country;
import io.takima.master3.store.core.pagination.PageSearch;
import io.takima.master3.store.core.pagination.SearchSpecification;
import io.takima.master3.store.core.utils.DatasourceSpy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import jakarta.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DisplayName("class CustomerDao")
class CustomerDaoTest {
    @Autowired
    DatasourceSpy spy;
    @Autowired
    CustomerDao customerDao;
    private PageSearch.Builder<Customer> psb;

    @BeforeEach
    void setupBuilder() {
        psb = new PageSearch.Builder<>();
    }

    @BeforeEach
    void resetSpy() {
        this.spy.reset();
    }

    @DisplayName("method 'findById(Long id)'")
    @Nested
    class FindMethod {
        @Nested
        @DisplayName("given an existing id")
        class WithValidId {
            Long customerId = 1L;

            @Test
            @DisplayName("should return the customer")
            void shouldReturnCustomer() {
                var customer = customerDao.findById(customerId);

                assertTrue(customer.isPresent());
                assertEquals("Joe", customer.get().getFirstName());
            }

            // TODO uncomment when in milestone hibernate-02.
            @Test
            @DisplayName("should return the customer with its cart")
            void shouldReturnCustomerCart() {
                var customer = customerDao.findById(customerId);

                assertTrue(customer.isPresent());
                assertNotNull(customer.get().getCart());
            }
        }
        @Nested
        @DisplayName("given an id that does not exist")
        class WithInvalidId {
            Long customerId = 99999L;

            @Test
            @DisplayName("should return an empty observable")
            void shouldReturnCustomer() {
                var customer = customerDao.findById(customerId);

                assertFalse(customer.isPresent());
            }
        }
    }

    @DisplayName("method 'findPage(search, limit, offset)'")
    @Nested
    class GetPage {

        @Nested
        @DisplayName("given a limit")
        class WithLimit {
            int limit = 10;

            @Test
            @DisplayName("should return a list with at most 'limit' element")
            void shouldLimitResults() {
                var customers = customerDao.findPage(psb.limit(limit).build());
                assertEquals(10, customers.getContent().size());

            }

        }

        @Nested
        @DisplayName("given an offset")
        class WithOffset {
            int offset = 2;

            @Test
            @DisplayName("should return a list that skips elements by 'offset'")
            void shouldOffsetResults() {
                var customers = customerDao.findPage(psb.offset(offset).build());
                assertEquals("William", customers.getContent().get(0).getFirstName());
            }
        }

        @Nested
        @DisplayName("given a search term")
        class WithSearch {
            @Test
            @DisplayName("should return a list with at most 'limit' element")
            void shouldSearch() {
                var customers = customerDao.findPage(new PageSearch
                        .Builder<Customer>()
                        .search(SearchSpecification.parse("lastName=%DaLtO%"))
                        .limit(2)
                        .offset(1)
                        .build());

                assertEquals(2, customers.getContent().size());
                assertEquals("Jack", customers.getContent().get(0).getFirstName()); //get ???
                customers.forEach(c -> assertEquals("DALTON", c.getLastName())); // c =>
            }
        }
    }

    @Nested
    @DisplayName("method 'count(search)'")
    class CountMethod {
        @Test
        @DisplayName("should return proper item count")
        void shouldCount() {
            var count = customerDao.count(new PageSearch
                    .Builder<Customer>()
                    .search(SearchSpecification.parse("lastName=%DaLtO%"))
                    .build());
            assertEquals(4, count);
        }
    }
}

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DisplayName("CustomerDaoTest method 'delete()'")
@Transactional  // cannot use @Nested with this. [issue](https://jira.spring.io/browse/SPR-15366)
class CustomerDaoDeleteMethodTest {

    @Autowired
    CustomerDao customerDao;

    @BeforeEach
    void assertJoeExists() {
        var customer = customerDao.findById(1L);
        assertEquals("Joe", customer.get().getFirstName());
    }

    @Test
    @DisplayName("should delete the entity")
    void shouldDelete() {
        customerDao.deleteById(1L);
        assertFalse(customerDao.findById(1L).isPresent());
    }
}

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DisplayName("CustomerDaoTest method 'create()'")
@Transactional  // cannot use @Nested with this. [issue](https://jira.spring.io/browse/SPR-15366)
class CustomerDaoCreateMethodTest {

    @Autowired
    CustomerDao customerDao;
    Customer customer = Customer.builder()
            .firstName("Lucky")
            .lastName("Luke")
            .email("llucky@takima.fr")
            .gender(Gender.MALE)
            .address(Address.builder()
                    .country(Country.BELGIUM)
                    .build())
            .build();

    @Test
    @DisplayName("should insert one entity")
    void shouldCreate() {
        long oldCount = customerDao.count();

        customerDao.save(customer);
        assertEquals(oldCount + 1, customerDao.count());
    }

    @Test
    @DisplayName("should assign a new ID")
    void shouldAssignId() {
        assertNull(customer.getId());
        customerDao.save(customer);
        assertNotNull(customer.getId());
    }
}

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DisplayName("CustomerDaoTest method 'create()'")
@Transactional  // cannot use @Nested with this. [issue](https://jira.spring.io/browse/SPR-15366)
class CustomerDaoUpdateMethodTest {

    @Autowired
    CustomerDao customerDao;
    Customer customer;

    @BeforeEach
    void getCustomer() {
        customer = customerDao.findById(1L).get();
        assertNotEquals("Jane", customer.getFirstName());
    }

    @Test
    @DisplayName("should update the entity")
    void shouldUpdate() {
        customer.setFirstName("Jane");
        customer.setLastName("CALAMITY");

        customerDao.save(customer);

        customer = customerDao.findById(1L).get();
        assertEquals("Jane", customer.getFirstName());
        assertEquals("CALAMITY", customer.getLastName());
    }
}

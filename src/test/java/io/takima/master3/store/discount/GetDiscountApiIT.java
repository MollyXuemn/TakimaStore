package io.takima.master3.store.discount;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc

@DisplayName("/api")
public class GetDiscountApiIT {

    @Autowired
    private MockMvc mvc;

    @AfterEach
    void clearDB(@Autowired Flyway flyway){
        flyway.clean();
        flyway.migrate();
    }

    @Nested
    @DisplayName("GET getDiscounts/")
    class GetDiscounts {

        @Test
        @DisplayName("with missing parameter 'customerId' should give status 404 NOT FOUND")
        void shouldGive404() throws Exception {
            mvc.perform(get("/api/customers/{customerId}/discounts", "")
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().isNotFound());
        }

        @Nested
        @DisplayName("with a valid customerId")
        class WithValidCustomerId {
            @Test
            @DisplayName("should give status 200 OK")
            void shouldGive200() throws Exception {
                mvc.perform(get("/api/customers/1/discounts")
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                        .andExpect(status().isOk());
            }

            @Test
            @DisplayName("give all discounts of the customer's cart")
            void shouldReturnDiscounts() throws Exception {
                mvc.perform(get("/api/customers/1/discounts")
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                        .andDo(print())
                        .andExpect(jsonPath("$.*", hasSize(2)))
                        .andExpect(jsonPath("[:2].id", is(List.of(3, 4))));
            }
        }
    }

    @Nested
    @DisplayName("GET addDiscounts/")
    class AddDiscounts {
        @Test
        @DisplayName("with missing parameters should give status 404 NOT FOUND")
        void shouldGive404() throws Exception {
            mvc.perform(put("/api/customers/{customerId}/discounts", "")
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("with missing parameter 'customerId' should give status 404 NOT FOUND")
        void shouldGive404ForCustomer() throws Exception {
            mvc.perform(put("/api/customers/{customerId}/discounts", "")
                            .param("code", "DEBUG_A_JAN2010")
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("with missing parameter 'code' should give status 400 BAD_REQUEST")
        void shouldGive400ForCode() throws Exception {
            mvc.perform(put("/api/customers/1/discounts")
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().isBadRequest());
        }

        @Nested
        @DisplayName("with a valid customerId and code")
        class WithValidCustomerId {
            @Test
            @DisplayName("should give status 200 OK")
            void shouldGive200() throws Exception {
                mvc.perform(put("/api/customers/1/discounts")
                                .param("code", "DEBUG_A_DEC2010")
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                        .andExpect(status().isOk());
            }

            @Test
            @DisplayName("add all discounts of the customer's cart")
            void shouldReturnDiscounts() throws Exception {
                mvc.perform(put("/api/customers/1/discounts")
                                .param("code", "DEBUG_A_DEC2010")
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                        .andExpect(status().isOk());

                mvc.perform(get("/api/customers/1/discounts")
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                        .andDo(print())
                        .andExpect(jsonPath("$.*", hasSize(3)));
            }
        }
    }

    @Nested
    @DisplayName("GET removeDiscounts/")
    class RemoveDiscounts {

        @Test
        @DisplayName("with missing parameters should give status 404 NOT_FOUND")
        void shouldGive404() throws Exception {
            mvc.perform(delete("/api/customers/{customerId}/discounts","")
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("with missing parameter 'customerId' should give status 404 NOT_FOUND")
        void shouldGive404ForCustomer() throws Exception {
            mvc.perform(delete("/api/customers/{customerId}/discounts", "")
                            .param("code", "DEBUG_A_DEC2010")
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("with missing parameter 'code' should give status 400 BAD_REQUEST")
        void shouldGive400ForCode() throws Exception {
            mvc.perform(delete("/api/customers/1/discounts")
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().isBadRequest());
        }

        @Nested
        @DisplayName("with a valid customerId and code")
        class WithValidCustomerId {
            @Test
            @DisplayName("should give status 200 OK")
            void shouldGive200() throws Exception {
                mvc.perform(put("/api/customers/1/discounts")
                                .param("code", "DEBUG_A_DEC2010")
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                        .andExpect(status().isOk());

                mvc.perform(delete("/api/customers/1/discounts")
                                .param("code", "DEBUG_A_DEC2010")
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                        .andExpect(status().isOk());
            }

            @Test
            @DisplayName("remove discount of the customer's cart")
            void shouldReturnDiscounts() throws Exception {

                mvc.perform(put("/api/customers/1/discounts")
                                .param("code", "DEBUG_A_DEC2010")
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                        .andExpect(status().isOk());

                mvc.perform(get("/api/customers/1/discounts")
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                        .andDo(print())
                        .andExpect(jsonPath("$.*", hasSize(3)));

                mvc.perform(delete("/api/customers/1/discounts")
                                .param("code", "DEBUG_A_DEC2010")
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                        .andExpect(status().isOk());

                mvc.perform(get("/api/customers/1/discounts")
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                        .andDo(print())
                        .andExpect(jsonPath("$.*", hasSize(2)));
            }
        }
    }
}


package io.takima.master3.store.discount;

// src/test/java/io/takima/master3/ma/discount/GetDiscountApiIT.java

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("/api")
public class GetDiscountApiIT {

    @Autowired
    private MockMvc mvc;
    @Nested
    @DisplayName("GET getDiscounts/")
    class GetDiscounts {

        @Test
        @DisplayName("with missing parameter 'customerId' should give status 400 BAD_REQUEST")
        void shouldGive400() throws Exception {
            mvc.perform(get("/api/getDiscounts")
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().isBadRequest());
        }

        @Nested
        @DisplayName("with a valid customerId")
        class WithValidCustomerId {
            @Test
            @DisplayName("should give status 200 OK")
            void shouldGive200() throws Exception {
                // TODO implement
                mvc.perform(get("/api/getDiscounts")
                        .param("customerId", "1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                        .andExpect(status().isOk());
            }

            @Test
            @DisplayName("give all discounts of the customer's cart")
            void shouldReturnDiscounts() throws Exception{
                // TODO implement. Get custpmerId=1 while clock fixed at 2010-12-15T12:00:00.00Z expect offers: {id=3, id=4}
                mvc.perform(get("/api/getDiscounts")
                                .param("customerId", "1")
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                        .andDo(print())
                        .andExpect(jsonPath("$.*", hasSize(3)))
                        .andExpect(jsonPath("[:2].id", is(List.of(6, 3))));

            }
        }
    }
    @Nested
    @DisplayName("GET addDiscount/")
    class AddDiscount {

        @Test
        @DisplayName("with missing parameter 'customerId' should give status 400 BAD_REQUEST")
        void shouldGive400() throws Exception {
            mvc.perform(get("/api/addDiscount")
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().isBadRequest());
        }

        @Nested
        @DisplayName("with a valid customerId")
        class WithValidCustomerId {
            @Test
            @DisplayName("should give status 200 OK")
            void shouldGive200() throws Exception {
                // TODO implement
                mvc.perform(get("/api/addDiscount")
                                .param("customerId", "1")
                                .param("code", "XMAS-2010")
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                        .andExpect(status().isOk());
            }

            @Test
            @DisplayName("give all discounts of the customer's cart")
            void shouldReturnDiscounts() throws Exception{
                // TODO implement. Get custpmerId=1 while clock fixed at 2010-12-15T12:00:00.00Z expect offers: {id=3, id=4}
                mvc.perform(get("/api/addDiscount")
                                .param("customerId", "1")
                                .param("code", "XMAS-2010")
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                        .andExpect(status().isOk());
                mvc.perform(get("/api/getDiscounts")
                                .param("customerId", "1")
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                        .andDo(print())
                        .andExpect(jsonPath("$.*", hasSize(4)))
                        .andExpect(jsonPath("[:2].id", is(List.of(6, 1))));

            }
        }
    }
    @Nested
    @DisplayName("GET removeDiscount/")
    class RemoveDiscount {
        @Test
        @DisplayName("with missing parameter 'customerId' should give status 400 BAD_REQUEST")
        void shouldGive400() throws Exception {
            mvc.perform(get("/api/removeDiscount")
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().isBadRequest());
        }

        @Nested
        @DisplayName("with a valid customerId")
        class WithValidCustomerId {
            @Test
            @DisplayName("should give status 200 OK")
            void shouldGive200() throws Exception {
                // TODO implement
                mvc.perform(get("/api/removeDiscount")
                                .param("customerId", "1")
                                .param("code", "XMAS-2010")
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                        .andExpect(status().isOk());
            }

            @Test
            @DisplayName("give all discounts of the customer's cart")
            void shouldReturnDiscounts() throws Exception{
                // TODO implement. Get custpmerId=1 while clock fixed at 2010-12-15T12:00:00.00Z expect offers: {id=3, id=4}
                mvc.perform(get("/api/removeDiscount")
                                .param("customerId", "1")
                                .param("code", "XMAS-2010")
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                        .andExpect(status().isOk());;
                mvc.perform(get("/api/addDiscount")
                                .param("customerId", "1")
                                .param("code", "XMAS-2010")
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                        .andExpect(status().isOk());
                mvc.perform(get("/api/getDiscounts")
                                .param("customerId", "1")
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                        .andDo(print())
                        .andExpect(jsonPath("$.*", hasSize(4)))
                        .andExpect(jsonPath("[:2].id", is(List.of(6, 1))));

            }
        }
    }
}

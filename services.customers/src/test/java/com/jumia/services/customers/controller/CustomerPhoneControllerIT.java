package com.jumia.services.customers.controller;

import com.jumia.services.customers.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CustomerPhoneControllerIT {
    @Autowired
    private MockMvc mockMvc;


    @Test
    public void testRetrievePhoneNumbersIfValidRequest() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/v1/numbers?page=0&size=5"))
                .andExpect(status().isOk()).
                 andExpect(jsonPath("$.phoneNumberList").isArray()).
                 andExpect(jsonPath("$.numberOfItems").isNumber())
                .andReturn();
    }

    @Test
    public void testRetrievePhoneNumbersIfCountryIsNotValid() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/v1/numbers?page=0&size=5&country=london"))
                .andExpect(status().isOk()).
                        andExpect(jsonPath("$.phoneNumberList").isArray())
                        .andExpect(jsonPath("$.numberOfItems", is(0)))
                .andReturn();
    }
}

package com.example.demo;

import com.example.demo.dto.CustomerDto;
import com.example.demo.mapper.CustomerMapper;
import com.example.demo.repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public class CustomerControllerIntegrationTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private CustomerRepository customerRepository;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        customerRepository.deleteAll();
    }

    @Test
    void testCreateCustomer() throws Exception {
        CustomerDto customerDto = new CustomerDto(1L, "First", "Middle", "Last", "first.last@example.com", "123-456-7890");

        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is("First")))
                .andExpect(jsonPath("$.emailAddress", is("first.last@example.com")));
    }

    @Test
    void testGetCustomerById() throws Exception {
        CustomerDto customer = new CustomerDto(1L, "First", "Middle", "Last", "first.last@example.com", "123-456-7890");
        CustomerDto savedCustomer = saveCustomer(customer);  // Helper to save a customer for testing

        mockMvc.perform(get("/api/customers/" + savedCustomer.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("First")))
                .andExpect(jsonPath("$.emailAddress", is("first.last@example.com")));
    }

    @Test
    void testGetAllCustomers() throws Exception {
        saveCustomer(new CustomerDto(null, "First", "Middle", "Last", "first.last@example.com", "123-456-7890"));
        saveCustomer(new CustomerDto(null, "First2", "Middle2", "Last2", "first2.last2@example.com", "123-456-7891"));

        mockMvc.perform(get("/api/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(2)))
                .andExpect(jsonPath("$[0].firstName", is("First")))
                .andExpect(jsonPath("$[1].firstName", is("First2")));
    }

    @Test
    void testUpdateCustomer() throws Exception {
        CustomerDto customer = new CustomerDto(1L, "First", "Middle", "Last", "first.last@example.com", "123-456-7890");
        CustomerDto savedCustomer = saveCustomer(customer);

        CustomerDto updatedCustomer = new CustomerDto(2L, "First2", "Middle2", "Last2", "first2.last2@example.com", "123-456-7891");

        mockMvc.perform(put("/api/customers/" + savedCustomer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedCustomer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("First2")))
                .andExpect(jsonPath("$.emailAddress", is("first2.last2@example.com")));
    }

    @Test
    void testDeleteCustomer() throws Exception {
        CustomerDto customer = new CustomerDto(1L, "First", "Middle", "Last", "first.last@example.com", "123-456-7890");
        CustomerDto savedCustomer = saveCustomer(customer);

        mockMvc.perform(delete("/api/customers/" + savedCustomer.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string("Customer deleted successfully!"));
    }

    private CustomerDto saveCustomer(CustomerDto customerDto) {
        return CustomerMapper.mapToCustomerDto(
                customerRepository.save(CustomerMapper.mapToCustomer(customerDto))
        );
    }
}

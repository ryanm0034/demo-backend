package com.example.demo.controller;

import com.example.demo.dto.CustomerDto;
import com.example.demo.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CustomerControllerTests {
    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCustomer() {
        CustomerDto newCustomer = new CustomerDto(1L, "First", "Middle", "Last", "first.last@example.com", "123-456-7890");
        when(customerService.createCustomer(newCustomer)).thenReturn(newCustomer);

        ResponseEntity<CustomerDto> response = customerController.createCustomer(newCustomer);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(newCustomer, response.getBody());
        verify(customerService, times(1)).createCustomer(newCustomer);
    }
}

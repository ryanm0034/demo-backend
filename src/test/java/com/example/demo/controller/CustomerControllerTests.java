package com.example.demo.controller;

import com.example.demo.dto.CustomerDto;
import com.example.demo.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

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

    @Test
    void testGetCustomerById() {
        CustomerDto customer = new CustomerDto(1L, "First", "Middle", "Last", "first.last@example.com", "123-456-7890");
        when(customerService.getCustomerById(1L)).thenReturn(customer);

        ResponseEntity<CustomerDto> response = customerController.getCustomerById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customer, response.getBody());
        verify(customerService, times(1)).getCustomerById(1L);
    }

    @Test
    void testGetAllCustomers() {
        List<CustomerDto> customers = Arrays.asList(
                new CustomerDto(1L, "First", "Middle", "Last", "first.last@example.com", "123-456-7890"),
                new CustomerDto(2L, "First2", "Middle2", "Last2", "first2.last2@example.com", "123-456-7891")
        );
        when(customerService.getAllCustomers()).thenReturn(customers);

        ResponseEntity<List<CustomerDto>> response = customerController.getAllCustomers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customers, response.getBody());
        verify(customerService, times(1)).getAllCustomers();
    }

    @Test
    void testUpdateCustomer() {
        CustomerDto updatedCustomer = new CustomerDto(1L, "First", "Middle", "Last", "first.last@example.com", "123-456-7890");
        when(customerService.updateCustomer(1L, updatedCustomer)).thenReturn(updatedCustomer);

        ResponseEntity<CustomerDto> response = customerController.updateCustomer(1L, updatedCustomer);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedCustomer, response.getBody());
        verify(customerService, times(1)).updateCustomer(1L, updatedCustomer);
    }

    @Test
    void testDeleteCustomer() {
        doNothing().when(customerService).deleteCustomer(1L);

        ResponseEntity<String> response = customerController.deleteCustomer(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Customer deleted successfully!", response.getBody());
        verify(customerService, times(1)).deleteCustomer(1L);
    }
}

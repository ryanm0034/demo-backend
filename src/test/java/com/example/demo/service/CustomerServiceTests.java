package com.example.demo.service;

import com.example.demo.dto.CustomerDto;
import com.example.demo.entity.Customer;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CustomerServiceTests {
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCustomer() {
        CustomerDto customerDto = new CustomerDto(1L, "First", "Middle", "Last", "first.last@example.com", "123-456-7890");
        Customer savedCustomer = new Customer(1L, "First", "Middle", "Last", "first.last@example.com", "123-456-7890");

        when(customerRepository.save(any())).thenReturn(savedCustomer);

        CustomerDto result = customerService.createCustomer(customerDto);

        assertNotNull(result);
        assertEquals(customerDto.getFirstName(), result.getFirstName());
        verify(customerRepository, times(1)).save(any());
    }

    @Test
    void testGetCustomerById_ExistingCustomer() {
        Customer customer = new Customer(1L, "First", "Middle", "Last", "first.last@example.com", "123-456-7890");
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        CustomerDto result = customerService.getCustomerById(1L);

        assertNotNull(result);
        assertEquals("First", result.getFirstName());
        verify(customerRepository, times(1)).findById(1L);
    }

    @Test
    void testGetCustomerById_CustomerNotFound() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> customerService.getCustomerById(1L));
        verify(customerRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAllCustomers() {
        List<Customer> customers = Arrays.asList(
                new Customer(1L, "First", "Middle", "Last", "first.last@example.com", "123-456-7890"),
                new Customer(2L, "First2", "Middle2", "Last2", "first2.last2@example.com", "123-456-7891")
        );

        when(customerRepository.findAll()).thenReturn(customers);

        List<CustomerDto> result = customerService.getAllCustomers();

        assertEquals(2, result.size());
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    void testUpdateCustomer_ExistingCustomer() {
        Customer existingCustomer = new Customer(1L, "First", "Middle", "Last", "first.last@example.com", "123-456-7890");
        CustomerDto updatedCustomerDto = new CustomerDto(1L, "First2", "Middle2", "Last2", "first2.last2@example.com", "123-456-7891");

        when(customerRepository.findById(1L)).thenReturn(Optional.of(existingCustomer));
        when(customerRepository.save(existingCustomer)).thenReturn(existingCustomer);

        CustomerDto result = customerService.updateCustomer(1L, updatedCustomerDto);

        assertNotNull(result);
        assertEquals("First2", result.getFirstName());
        verify(customerRepository, times(1)).save(existingCustomer);
    }

    @Test
    void testUpdateCustomer_CustomerNotFound() {
        CustomerDto updatedCustomerDto = new CustomerDto(1L, "First2", "Middle2", "Last2", "first2.last2@example.com", "123-456-7891");

        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> customerService.updateCustomer(1L, updatedCustomerDto));
        verify(customerRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteCustomer_ExistingCustomer() {
        Customer existingCustomer = new Customer(1L, "First", "Middle", "Last", "first.last@example.com", "123-456-7890");
        when(customerRepository.findById(1L)).thenReturn(Optional.of(existingCustomer));
        doNothing().when(customerRepository).deleteById(1L);

        customerService.deleteCustomer(1L);

        verify(customerRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteCustomer_CustomerNotFound() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> customerService.deleteCustomer(1L));
        verify(customerRepository, times(1)).findById(1L);
    }
}

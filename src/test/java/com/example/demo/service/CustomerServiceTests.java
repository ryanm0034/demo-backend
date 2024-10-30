package com.example.demo.service;

import com.example.demo.dto.CustomerDto;
import com.example.demo.entity.Customer;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
}

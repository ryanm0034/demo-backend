package com.example.demo.service.impl;

import com.example.demo.dto.CustomerDto;
import com.example.demo.entity.Customer;
import com.example.demo.mapper.CustomerMapper;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.service.CustomerService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private CustomerRepository customerRepository;
    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Override
    public CustomerDto createCustomer(CustomerDto customerDto) {
        logger.info("Creating customer: {}", customerDto.getEmailAddress());
        Customer customer = CustomerMapper.mapToCustomer(customerDto);
        Customer savedCustomer = customerRepository.save(customer);
        logger.info("Customer created successfully with ID: {}", savedCustomer.getId());
        return CustomerMapper.mapToCustomerDto(savedCustomer);
    }

    @Override
    public CustomerDto getCustomerById(Long customerId) {
        return null;
    }

    @Override
    public List<CustomerDto> getAllCustomers() {
        return List.of();
    }

    @Override
    public CustomerDto updateCustomer(Long customerId, CustomerDto updatedCustomer) {
        return null;
    }

    @Override
    public void deleteCustomer(Long customerId) {

    }
}

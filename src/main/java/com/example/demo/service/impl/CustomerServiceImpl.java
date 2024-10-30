package com.example.demo.service.impl;

import com.example.demo.dto.CustomerDto;
import com.example.demo.entity.Customer;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.CustomerMapper;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.service.CustomerService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        logger.info("Fetching customer with ID: {}", customerId);
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> {
                    logger.error("Customer not found with ID: {}", customerId);
                    return new ResourceNotFoundException("Customer does not exist with given id: " + customerId);
                });

        logger.info("Successfully fetched customer with ID: {}", customerId);
        return CustomerMapper.mapToCustomerDto(customer);
    }

    @Override
    public List<CustomerDto> getAllCustomers() {
        logger.info("Fetching all customers");
        List<Customer> customers = customerRepository.findAll();
        logger.info("Successfully fetched all customers");
        return customers.stream().map(CustomerMapper::mapToCustomerDto).collect(Collectors.toList());
    }

    @Override
    public CustomerDto updateCustomer(Long customerId, CustomerDto updatedCustomer) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new ResourceNotFoundException("Customer does not exist with given id: " + customerId));

        customer.setFirstName(updatedCustomer.getFirstName());
        customer.setMiddleName(updatedCustomer.getMiddleName());
        customer.setLastName(updatedCustomer.getLastName());
        customer.setEmailAddress(updatedCustomer.getEmailAddress());
        customer.setPhoneNumber(updatedCustomer.getPhoneNumber());

        Customer savedCustomer = customerRepository.save(customer);

        return CustomerMapper.mapToCustomerDto(savedCustomer);
    }

    @Override
    public void deleteCustomer(Long customerId) {
        customerRepository.findById(customerId).orElseThrow(
                () -> new ResourceNotFoundException("Customer does not exist with given id: " + customerId));

        customerRepository.deleteById(customerId);
    }
}

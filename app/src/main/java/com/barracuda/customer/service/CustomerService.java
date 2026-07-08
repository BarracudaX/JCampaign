package com.barracuda.customer.service;

import com.barracuda.customer.domain.Customer;
import com.barracuda.customer.dto.CreateCustomerForm;
import com.barracuda.customer.dto.CustomerDTO;
import com.barracuda.customer.mapping.CustomerMapper;
import com.barracuda.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerDTO createCustomer(CreateCustomerForm createCustomerForm) {
        Customer customer = CustomerMapper.INSTANCE.customerCreateFormToCustomer(createCustomerForm);

        return CustomerMapper.INSTANCE.customerToCustomerDTO(customerRepository.save(customer));
    }

}

package com.barracuda.app.customer.service;

import com.barracuda.app.customer.domain.Customer;
import com.barracuda.app.customer.dto.CreateCustomerForm;
import com.barracuda.app.customer.dto.CustomerDTO;
import com.barracuda.app.customer.mapping.CustomerMapper;
import com.barracuda.app.customer.repository.CustomerRepository;
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

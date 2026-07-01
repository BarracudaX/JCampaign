package com.barracuda.jcampaign.customer.service;

import com.barracuda.jcampaign.customer.domain.Customer;
import com.barracuda.jcampaign.customer.dto.CreateCustomerForm;
import com.barracuda.jcampaign.customer.dto.CustomerDTO;
import com.barracuda.jcampaign.customer.mapping.CustomerMapper;
import com.barracuda.jcampaign.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerDTO createCustomer(CreateCustomerForm createCustomerForm) {
        Customer customer = CustomerMapper.INSTANCE.customerCreateFormToCustomer(createCustomerForm);

        customerRepository.save(customer);
        
        return CustomerMapper.INSTANCE.customerToCustomerDTO(customer);
    }

}

package com.barracuda.jcampaign.service;

import com.barracuda.jcampaign.domain.Customer;
import com.barracuda.jcampaign.dto.CreateCustomerForm;
import com.barracuda.jcampaign.dto.CustomerDTO;
import com.barracuda.jcampaign.mapping.CustomerMapper;
import com.barracuda.jcampaign.repository.CustomerRepository;
import jakarta.transaction.Transactional;
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

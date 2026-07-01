package com.barracuda.jcampaign.customer.mapping;

import com.barracuda.jcampaign.customer.domain.Customer;
import com.barracuda.jcampaign.customer.dto.CreateCustomerForm;
import com.barracuda.jcampaign.customer.dto.CustomerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    CustomerDTO customerToCustomerDTO(Customer customer);

    Customer customerCreateFormToCustomer(CreateCustomerForm customerForm);
}

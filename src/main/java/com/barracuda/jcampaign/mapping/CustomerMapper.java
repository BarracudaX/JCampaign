package com.barracuda.jcampaign.mapping;

import com.barracuda.jcampaign.domain.Customer;
import com.barracuda.jcampaign.dto.CreateCustomerForm;
import com.barracuda.jcampaign.dto.CustomerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    CustomerDTO customerToCustomerDTO(Customer customer);

    Customer customerCreateFormToCustomer(CreateCustomerForm customerForm);
}

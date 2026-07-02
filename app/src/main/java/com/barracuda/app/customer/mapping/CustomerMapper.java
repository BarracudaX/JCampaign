package com.barracuda.app.customer.mapping;

import com.barracuda.app.customer.domain.Customer;
import com.barracuda.app.customer.dto.CreateCustomerForm;
import com.barracuda.app.customer.dto.CustomerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

//@Mapper
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    CustomerDTO customerToCustomerDTO(Customer customer);

    Customer customerCreateFormToCustomer(CreateCustomerForm customerForm);
}

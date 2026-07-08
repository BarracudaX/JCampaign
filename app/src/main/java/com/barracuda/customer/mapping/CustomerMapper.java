package com.barracuda.customer.mapping;

import com.barracuda.customer.domain.Customer;
import com.barracuda.customer.dto.CreateCustomerForm;
import com.barracuda.customer.dto.CustomerDTO;
import org.mapstruct.factory.Mappers;

//@Mapper
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    CustomerDTO customerToCustomerDTO(Customer customer);

    Customer customerCreateFormToCustomer(CreateCustomerForm customerForm);
}

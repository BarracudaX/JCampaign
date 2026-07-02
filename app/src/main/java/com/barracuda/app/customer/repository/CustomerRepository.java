package com.barracuda.app.customer.repository;

import com.barracuda.app.customer.domain.Customer;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends ListCrudRepository<Customer, Long> {

}

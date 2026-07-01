package com.barracuda.jcampaign.customer.repository;

import com.barracuda.jcampaign.customer.domain.Customer;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends ListCrudRepository<Customer, Long> {

}

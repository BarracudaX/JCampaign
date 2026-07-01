package com.barracuda.jcampaign.customer.repository;

import com.barracuda.jcampaign.customer.domain.LoyaltyCard;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoyaltyCardRepository extends ListCrudRepository<LoyaltyCard, Long> {
}

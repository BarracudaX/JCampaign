package com.barracuda.jcampaign.loyaltycard.repository;

import com.barracuda.jcampaign.loyaltycard.domain.LoyaltyCard;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoyaltyCardRepository extends ListCrudRepository<LoyaltyCard, Long> {
}

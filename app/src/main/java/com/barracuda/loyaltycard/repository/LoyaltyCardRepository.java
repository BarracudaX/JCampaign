package com.barracuda.loyaltycard.repository;

import com.barracuda.loyaltycard.domain.LoyaltyCard;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoyaltyCardRepository extends ListCrudRepository<LoyaltyCard, Long> {
}

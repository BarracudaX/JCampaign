package com.barracuda.jcampaign.customer.domain;

import com.barracuda.jcampaign.ErrorMessages;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

import static com.barracuda.jcampaign.ErrorMessages.*;

@Table(name = "customers")
public record LoyaltyCard (
        @Id
        Long id,

        String name,

        @CreatedDate
        LocalDate createdDate,

        LocalDate expiryDate,

        CardState state,

        @Setter
        long points
){




    public LoyaltyCard withExpiryDate(LocalDate expiryDate) {
        if(expiryDate.isBefore(LocalDate.now())) {
            throw  new IllegalArgumentException(ErrorMessages.INVALID_EXPIRY_DATE_ERROR_MESSAGE);
        }

        return new LoyaltyCard(id, name, createdDate, expiryDate, state, points);
    }

    public boolean isLocked() {
        return state == CardState.LOCKED;
    }

    public LoyaltyCard awardPoints(long points) {

        return switch (state) {
            case LOCKED -> throw new IllegalStateException(INVALID_ADD_OPERATION_ERROR_MESSAGE);
            case UNLOCKED -> {
                if (points < 0) {
                    throw new IllegalArgumentException(INVALID_ADD_POINTS_VALUE_ERROR_MESSAGE);
                }
                yield new LoyaltyCard(id, name, createdDate, expiryDate, CardState.UNLOCKED, this.points + points);
            }
        };

    }

    public LoyaltyCard redeemPoints(long points) {
        return switch (state){
            case LOCKED -> throw new IllegalStateException(INVALID_SUBTRACT_OPERATION_ERROR_MESSAGE);
            case UNLOCKED -> {
                if (points < 0) {
                    throw new IllegalArgumentException(INVALID_SUBTRACTING_NEGATIVE_POINTS_ERROR_MESSAGE);
                }
                if (points > this.points) {
                    throw new IllegalArgumentException(INVALID_SUBTRACTING_MORE_THAN_POSSIBLE_POINTS_ERROR_MESSAGE.formatted(points, this.points));
                }
                yield new LoyaltyCard(id, name, createdDate, expiryDate, CardState.UNLOCKED, this.points - points);
            }
        };
    }

    public LoyaltyCard lock() {
        return new LoyaltyCard(id, name, createdDate, expiryDate, CardState.LOCKED, points);
    }

    public LoyaltyCard unlock() {
        return new LoyaltyCard(id, name, createdDate, expiryDate, CardState.UNLOCKED, points);
    }

}

package com.barracuda.jcampaign.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
public class LoyaltyCard {

    public static final String INVALID_EXPIRY_DATE_ERROR_MESSAGE = "Expiry date cannot be before now";
    public static final String INVALID_ADD_POINTS_VALUE_ERROR_MESSAGE = "Only positive number of points can be added to the loyalty card";
    public static final String INVALID_SUBTRACTING_NEGATIVE_POINTS_ERROR_MESSAGE = "Only positive number of points can be subtracted from the loyalty card";
    public static final String INVALID_ADD_OPERATION_ERROR_MESSAGE = "Loyalty card is locked. No points can be added to the loyalty card";
    public static final String INVALID_SUBTRACT_OPERATION_ERROR_MESSAGE = "Loyalty card is locked. No points can be subtracted from the loyalty card";
    public static final String INVALID_SUBTRACTING_MORE_THAN_POSSIBLE_POINTS_ERROR_MESSAGE = "Trying to subtract %d points from a loyalty card that only has %d points";

    private enum CardState{
        LOCKED {
            @Override
            public void addPoints(LoyaltyCard loyaltyCard, long points) {
                throw new IllegalStateException(INVALID_ADD_OPERATION_ERROR_MESSAGE);
            }

            @Override
            public void subtractPoints(LoyaltyCard loyaltyCard, long points) {
                throw new IllegalStateException(INVALID_SUBTRACT_OPERATION_ERROR_MESSAGE);
            }
        },

        UNLOCKED {
            @Override
            public void addPoints(LoyaltyCard loyaltyCard, long points) {
                if(points < 0) {
                    throw  new IllegalArgumentException(INVALID_ADD_POINTS_VALUE_ERROR_MESSAGE);
                }
                loyaltyCard.setPoints(loyaltyCard.getPoints() + points);
            }

            @Override
            public void subtractPoints(LoyaltyCard loyaltyCard, long points) {
                if(points < 0) {
                    throw new IllegalArgumentException(INVALID_SUBTRACTING_NEGATIVE_POINTS_ERROR_MESSAGE);
                }

                if(points > loyaltyCard.getPoints()) {
                    throw new IllegalArgumentException(INVALID_SUBTRACTING_MORE_THAN_POSSIBLE_POINTS_ERROR_MESSAGE.formatted(points,loyaltyCard.getPoints()));
                }

                loyaltyCard.setPoints(loyaltyCard.getPoints() - points);
            }
        };

        public abstract void addPoints(LoyaltyCard loyaltyCard, long points);

        public abstract void subtractPoints(LoyaltyCard loyaltyCard,long points);

    }

    @Id
    private Long id;

    private String name;

    @ManyToOne
    private Customer owner;

    @CreationTimestamp
    private LocalDate createdDate;

    private LocalDate expiryDate;

    private CardState state = CardState.UNLOCKED;

    private long points;


    public Long getId() {
        return id;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        if(expiryDate.isBefore(LocalDate.now())) {
            throw  new IllegalArgumentException(INVALID_EXPIRY_DATE_ERROR_MESSAGE);
        }
        this.expiryDate = expiryDate;
    }

    public String getName() {
        return name;
    }

    public Customer getOwner() {
        return owner;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public boolean isLocked() {
        return state == CardState.LOCKED;
    }

    public long getPoints() {
        return points;
    }

    public void addPoints(long points) {
        this.state.addPoints(this,points);
    }

    public void subtractPoints(long points) {
        this.state.subtractPoints(this,points);
    }

    public void lock() {
        this.state = CardState.LOCKED;
    }

    public void unlock() {
        this.state = CardState.UNLOCKED;
    }

    public void setPoints(long points) {
        this.points = points;
    }
}

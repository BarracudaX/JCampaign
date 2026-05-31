package com.barracuda.jcampaign.domain;

import com.barracuda.jcampaign.ErrorMessages;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
public class LoyaltyCard {
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
            throw  new IllegalArgumentException(ErrorMessages.INVALID_EXPIRY_DATE_ERROR_MESSAGE);
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

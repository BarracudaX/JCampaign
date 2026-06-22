package com.barracuda.jcampaign.domain;

import com.barracuda.jcampaign.ErrorMessages;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

import static com.barracuda.jcampaign.ErrorMessages.*;

@Entity
@Table(name = "loyalty_cards")
@Getter
@ToString
public class LoyaltyCard {

    @Id
    private Long id;

    private String name;

    @CreationTimestamp
    private LocalDate createdDate;

    private LocalDate expiryDate;

    private CardState state = CardState.UNLOCKED;

    @Setter
    private long points;


    public void setExpiryDate(LocalDate expiryDate) {
        if(expiryDate.isBefore(LocalDate.now())) {
            throw  new IllegalArgumentException(ErrorMessages.INVALID_EXPIRY_DATE_ERROR_MESSAGE);
        }
        this.expiryDate = expiryDate;
    }

    public boolean isLocked() {
        return state == CardState.LOCKED;
    }

    public void addPoints(long points) {

        switch (state) {
            case LOCKED -> throw new IllegalStateException(INVALID_ADD_OPERATION_ERROR_MESSAGE);
            case UNLOCKED -> {
                if (points < 0) {
                    throw new IllegalArgumentException(INVALID_ADD_POINTS_VALUE_ERROR_MESSAGE);
                }
                setPoints(this.points + points);
            }
        }

    }

    public void subtractPoints(long points) {
        switch (state){
            case LOCKED -> throw new IllegalStateException(INVALID_SUBTRACT_OPERATION_ERROR_MESSAGE);
            case UNLOCKED -> {
                if (points < 0) {
                    throw new IllegalArgumentException(INVALID_SUBTRACTING_NEGATIVE_POINTS_ERROR_MESSAGE);
                }
                if (points > getPoints()) {
                    throw new IllegalArgumentException(INVALID_SUBTRACTING_MORE_THAN_POSSIBLE_POINTS_ERROR_MESSAGE.formatted(points, getPoints()));
                }
                setPoints(this.points - points);
            }
        }
    }

    public void lock() {
        this.state = CardState.LOCKED;
    }

    public void unlock() {
        this.state = CardState.UNLOCKED;
    }

}

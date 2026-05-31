package com.barracuda.jcampaign.domain;

import static com.barracuda.jcampaign.ErrorMessages.*;

public enum CardState {

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
            if (points < 0) {
                throw new IllegalArgumentException(INVALID_ADD_POINTS_VALUE_ERROR_MESSAGE);
            }
            loyaltyCard.setPoints(loyaltyCard.getPoints() + points);
        }

        @Override
        public void subtractPoints(LoyaltyCard loyaltyCard, long points) {
            if (points < 0) {
                throw new IllegalArgumentException(INVALID_SUBTRACTING_NEGATIVE_POINTS_ERROR_MESSAGE);
            }

            if (points > loyaltyCard.getPoints()) {
                throw new IllegalArgumentException(INVALID_SUBTRACTING_MORE_THAN_POSSIBLE_POINTS_ERROR_MESSAGE.formatted(points, loyaltyCard.getPoints()));
            }

            loyaltyCard.setPoints(loyaltyCard.getPoints() - points);
        }
    };

    public abstract void addPoints(LoyaltyCard loyaltyCard, long points);

    public abstract void subtractPoints(LoyaltyCard loyaltyCard, long points);

}

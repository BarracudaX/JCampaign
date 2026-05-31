package com.barracuda.jcampaign.unit;

import static org.assertj.core.api.Assertions.*;
import com.barracuda.jcampaign.domain.LoyaltyCard;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class LoyaltyCardTest {

    private final LoyaltyCard loyaltyCard = new LoyaltyCard();


    /**
     * Expiry date is invalid when the date is before current date.
     */
    @Test
    void shouldThrowIAEWhenExpiryDateIsInvalid() {
        LocalDate invalidExpiryDate = LocalDate.now().minusDays(1); //invalid

        assertThatThrownBy(() -> loyaltyCard.setExpiryDate(invalidExpiryDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(LoyaltyCard.INVALID_EXPIRY_DATE_ERROR_MESSAGE);
    }

    @Test
    void shouldChangeExpiryDate() {
        loyaltyCard.setExpiryDate(LocalDate.now().plusDays(10));
        LocalDate validExpiryDate = LocalDate.now();

        assertThatCode(() -> loyaltyCard.setExpiryDate(validExpiryDate)).doesNotThrowAnyException();
        assertThat(loyaltyCard.getExpiryDate()).isEqualTo(validExpiryDate);
    }

    @Test
    void shouldAddMorePointsToTheCard() {
        assertThat(loyaltyCard.getPoints()).isZero();

        loyaltyCard.addPoints(100);

        assertThat(loyaltyCard.getPoints()).isEqualTo(100);
    }

    @Test
    void shouldThrowIAEWhenTryingToAddNegativePoints() {
        loyaltyCard.addPoints(100);

        assertThatThrownBy(() -> loyaltyCard.addPoints(-1)).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(LoyaltyCard.INVALID_ADD_POINTS_VALUE_ERROR_MESSAGE);
        assertThat(loyaltyCard.getPoints()).isEqualTo(100);
    }

    @Test
    void shouldSubtractPointsFromTheCard() {
        loyaltyCard.addPoints(100);

        loyaltyCard.subtractPoints(50);

        assertThat(loyaltyCard.getPoints()).isEqualTo(50);
    }

    @Test
    void shouldThrowIAEWhenTryingToSubtractMorePointsThanTheCardHas() {
        loyaltyCard.addPoints(100);

        assertThatThrownBy(() -> loyaltyCard.subtractPoints(110)).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(LoyaltyCard.INVALID_SUBTRACTING_MORE_THAN_POSSIBLE_POINTS_ERROR_MESSAGE.formatted(110,100));

        assertThat(loyaltyCard.getPoints()).isEqualTo(100);
    }

    @Test
    void shouldThrowIAEWhenSubtractingNegativePoints() {
        loyaltyCard.addPoints(100);

        assertThatThrownBy(() -> loyaltyCard.subtractPoints(-10)).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(LoyaltyCard.INVALID_SUBTRACTING_NEGATIVE_POINTS_ERROR_MESSAGE);

        assertThat(loyaltyCard.getPoints()).isEqualTo(100);
    }

    @Test
    void shouldBeAbleToAddZeroPoints() {
        loyaltyCard.addPoints(100);

        assertThatCode(() -> loyaltyCard.addPoints(0)).doesNotThrowAnyException();

        assertThat(loyaltyCard.getPoints()).isEqualTo(100);
    }

    @Test
    void shouldBeAbleToSubtractZeroPoints() {
        loyaltyCard.addPoints(100);

        assertThatCode(() -> loyaltyCard.subtractPoints(0)).doesNotThrowAnyException();

        assertThat(loyaltyCard.getPoints()).isEqualTo(100);
    }

    @Test
    void shouldBeAbleToLockCard() {
        assertThat(loyaltyCard.isLocked()).isFalse();

        loyaltyCard.lock();

        assertThat(loyaltyCard.isLocked()).isTrue();
    }

    @Test
    void shouldBeAbleToUnlockCard() {
        loyaltyCard.lock();
        assertThat(loyaltyCard.isLocked()).isTrue();

        loyaltyCard.unlock();

        assertThat(loyaltyCard.isLocked()).isFalse();
    }

    @Test
    void shouldNotBeAbleToAddPointsToALockedCard() {
        loyaltyCard.lock();

        assertThatThrownBy(() -> loyaltyCard.addPoints(100)).isInstanceOf(IllegalStateException.class).hasMessageContaining(LoyaltyCard.INVALID_ADD_OPERATION_ERROR_MESSAGE);
    }

    @Test
    void shouldNotBeAbleToSubtractPointsFromALockedCard() {
        loyaltyCard.lock();

        assertThatThrownBy(() -> loyaltyCard.subtractPoints(10)).isInstanceOf(IllegalStateException.class).hasMessageContaining(LoyaltyCard.INVALID_SUBTRACT_OPERATION_ERROR_MESSAGE);
    }
}

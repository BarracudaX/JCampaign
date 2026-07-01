package com.barracuda.jcampaign.unit;

import static org.assertj.core.api.Assertions.*;

import com.barracuda.jcampaign.ErrorMessages;
import com.barracuda.jcampaign.loyaltycard.domain.CardState;
import com.barracuda.jcampaign.loyaltycard.domain.LoyaltyCard;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class LoyaltyCardTest {

    private LoyaltyCard loyaltyCard = new LoyaltyCard(1L,"CARD",LocalDate.now(),LocalDate.now().plusDays(1), CardState.UNLOCKED,0);


    /**
     * Expiry date is invalid when the date is before current date.
     */
    @Test
    void shouldThrowIAEWhenExpiryDateIsInvalid() {
        LocalDate invalidExpiryDate = LocalDate.now().minusDays(1); //invalid

        assertThatThrownBy(() -> loyaltyCard.withExpiryDate(invalidExpiryDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorMessages.INVALID_EXPIRY_DATE_ERROR_MESSAGE);
    }

    @Test
    void shouldChangeExpiryDate() {
        LocalDate validExpiryDate = LocalDate.now();

        assertThat(loyaltyCard.withExpiryDate(validExpiryDate).expiryDate()).isEqualTo(validExpiryDate);
    }

    @Test
    void shouldAddMorePointsToTheCard() {
        assertThat(loyaltyCard.points()).isZero();

        assertThat(loyaltyCard.awardPoints(100).points()).isEqualTo(100);
    }

    @Test
    void shouldThrowIAEWhenTryingToAddNegativePoints() {
        loyaltyCard = loyaltyCard.awardPoints(100);

        assertThatThrownBy(() -> loyaltyCard.awardPoints(-1)).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(ErrorMessages.INVALID_ADD_POINTS_VALUE_ERROR_MESSAGE);
        assertThat(loyaltyCard.points()).isEqualTo(100);
    }

    @Test
    void shouldRedeemPointsFromTheCard() {
        assertThat(loyaltyCard.awardPoints(100).redeemPoints(50).points()).isEqualTo(50);
    }

    @Test
    void shouldThrowIAEWhenTryingToSubtractMorePointsThanTheCardHas() {
        assertThatThrownBy(() -> loyaltyCard.awardPoints(100).redeemPoints(110)).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(ErrorMessages.INVALID_SUBTRACTING_MORE_THAN_POSSIBLE_POINTS_ERROR_MESSAGE.formatted(110,100));
    }

    @Test
    void shouldThrowIAEWhenSubtractingNegativePoints() {
        assertThatThrownBy(() -> loyaltyCard.redeemPoints(-10)).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(ErrorMessages.INVALID_SUBTRACTING_NEGATIVE_POINTS_ERROR_MESSAGE);
    }

    @Test
    void shouldBeAbleToAddZeroPoints() {
        assertThatCode(() -> loyaltyCard.awardPoints(0)).doesNotThrowAnyException();
    }

    @Test
    void shouldBeAbleToSubtractZeroPoints() {
        assertThatCode(() -> loyaltyCard.redeemPoints(0)).doesNotThrowAnyException();
    }

    @Test
    void shouldBeAbleToLockCard() {
        assertThat(loyaltyCard.lock().isLocked()).isTrue();
    }

    @Test
    void shouldBeAbleToUnlockCard() {
        assertThat(loyaltyCard.lock().unlock().isLocked()).isFalse();
    }

    @Test
    void shouldNotBeAbleToAwardPointsToALockedCard() {
        assertThatThrownBy(() -> loyaltyCard.lock().awardPoints(100)).isInstanceOf(IllegalStateException.class).hasMessageContaining(ErrorMessages.INVALID_ADD_OPERATION_ERROR_MESSAGE);
    }

    @Test
    void shouldNotBeAbleToRedeemPointsFromALockedCard() {
        assertThatThrownBy(() -> loyaltyCard.lock().redeemPoints(10)).isInstanceOf(IllegalStateException.class).hasMessageContaining(ErrorMessages.INVALID_SUBTRACT_OPERATION_ERROR_MESSAGE);
    }
}

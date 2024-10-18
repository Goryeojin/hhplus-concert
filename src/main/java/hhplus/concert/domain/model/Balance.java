package hhplus.concert.domain.model;

import hhplus.concert.support.exception.CustomException;
import hhplus.concert.support.exception.ErrorCode;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record Balance(
        Long id,
        Long userId,
        Long amount,
        LocalDateTime lastUpdatedAt
) {
    public Balance charge(Long amount) {
        return Balance.builder()
                .id(this.id)
                .userId(this.userId)
                .amount(this.amount + amount)
                .lastUpdatedAt(LocalDateTime.now())
                .build();
    }

    public Balance useBalance(int useAmount) {
        if (this.amount < useAmount) {
            throw new CustomException(ErrorCode.PAYMENT_FAILED_AMOUNT);
        }
        return Balance.builder()
                .id(id)
                .userId(userId)
                .amount(amount - useAmount)
                .lastUpdatedAt(LocalDateTime.now())
                .build();
    }
}

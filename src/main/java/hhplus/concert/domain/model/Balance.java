package hhplus.concert.domain.model;

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
}

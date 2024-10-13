package hhplus.concert.interfaces.dto;

import lombok.Builder;

public class BalanceDto {

    public record Request (
            Long amount
    ) {
    }

    @Builder
    public record Response (
        Long userId,
        Long currentAmount
    ) {
    }
}

package hhplus.concert.interfaces.dto;

import lombok.Builder;

public class BalanceDto {

    @Builder
    public record Response (
        Long userId,
        Long currentAmount
    ) {
    }
}

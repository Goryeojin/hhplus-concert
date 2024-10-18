package hhplus.concert.interfaces.dto;

import jakarta.validation.constraints.Min;
import lombok.Builder;

public class BalanceDto {

    public record BalanceRequest (
            @Min(value = 1, message = "Amount must be greater then zero.")
            Long amount
    ) {
    }

    @Builder
    public record BalanceResponse (
        Long userId,
        Long currentAmount
    ) {
    }
}

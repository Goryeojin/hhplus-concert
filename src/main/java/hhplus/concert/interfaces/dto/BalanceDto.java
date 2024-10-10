package hhplus.concert.interfaces.dto;

import lombok.Builder;
import lombok.Getter;

public class BalanceDto {

    @Getter
    @Builder
    public static class Response {
        private Long userId;
        private Long currentAmount;
    }
}

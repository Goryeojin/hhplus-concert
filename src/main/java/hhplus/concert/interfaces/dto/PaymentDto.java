package hhplus.concert.interfaces.dto;

import hhplus.concert.support.type.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class PaymentDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        private Long userId;
        private Long reservationId;
    }

    @Getter
    @Builder
    public static class Response {
        private Long paymentId;
        private Long amount;
        private PaymentStatus paymentStatus;
    }
}

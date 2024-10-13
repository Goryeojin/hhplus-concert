package hhplus.concert.interfaces.dto;

import hhplus.concert.support.type.PaymentStatus;
import lombok.Builder;

public class PaymentDto {

    @Builder
    public record Request (
            Long userId,
            Long reservationId
    ) {
    }

    @Builder
    public record Response (
        Long paymentId,
        Long amount,
        PaymentStatus paymentStatus
    ) {
    }
}

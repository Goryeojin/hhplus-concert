package hhplus.concert.domain.model;

import hhplus.concert.support.type.PaymentStatus;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record Payment(
        Long id,
        Long reservationId,
        Long userId,
        Long amount,
        LocalDateTime paymentAt,
        PaymentStatus status
) {
}

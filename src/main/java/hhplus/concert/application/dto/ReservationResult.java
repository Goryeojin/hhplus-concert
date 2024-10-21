package hhplus.concert.application.dto;

import hhplus.concert.domain.model.Seat;
import hhplus.concert.support.type.ReservationStatus;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ReservationResult(

        Long reservationId,
        Long concertId,
        LocalDateTime concertAt,
        Seat seat,
        ReservationStatus status
) {
}

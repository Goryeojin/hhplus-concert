package hhplus.concert.application.dto;

import hhplus.concert.interfaces.dto.SeatDto;
import hhplus.concert.support.type.ReservationStatus;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ReservationResponse(

        Long reservationId,
        Long concertId,
        LocalDateTime concertAt,
        SeatDto seat,
        ReservationStatus status
) {
}

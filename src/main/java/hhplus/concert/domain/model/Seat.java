package hhplus.concert.domain.model;

import hhplus.concert.support.type.SeatStatus;
import lombok.Builder;

@Builder
public record Seat(
    Long id,
    Long concertScheduleId,
    int seatNo,
    SeatStatus status,
    int seatPrice
) {
}

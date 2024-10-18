package hhplus.concert.domain.model;

import hhplus.concert.support.exception.CustomException;
import hhplus.concert.support.exception.ErrorCode;
import hhplus.concert.support.type.SeatStatus;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record Seat(
    Long id,
    Long concertScheduleId,
    int seatNo,
    SeatStatus status,
    LocalDateTime reservationAt,
    int seatPrice
) {
    public void checkStatus() {
        if (status.equals(SeatStatus.UNAVAILABLE)) {
            throw new CustomException(ErrorCode.SEAT_UNAVAILABLE);
        }
    }

    public Seat assign() {
        return Seat.builder()
                .id(this.id)
                .concertScheduleId(this.concertScheduleId)
                .seatNo(this.seatNo)
                .status(SeatStatus.UNAVAILABLE)
                .reservationAt(LocalDateTime.now())
                .seatPrice(this.seatPrice)
                .build();
    }
}

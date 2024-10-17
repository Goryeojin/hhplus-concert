package hhplus.concert.domain.model;

import hhplus.concert.support.type.ReservationStatus;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record Reservation(
        Long id,
        Long concertId,
        Long scheduleId,
        Long seatId,
        Long userId,
        ReservationStatus status,
        LocalDateTime reservationAt
) {
    public static Reservation create(ConcertSchedule schedule, Long seatId, Long userId) {
        return Reservation.builder()
                .concertId(schedule.concertId())
                .scheduleId(schedule.id())
                .seatId(seatId)
                .userId(userId)
                .status(ReservationStatus.PAYMENT_WAITING)
                .reservationAt(LocalDateTime.now())
                .build();
    }
}

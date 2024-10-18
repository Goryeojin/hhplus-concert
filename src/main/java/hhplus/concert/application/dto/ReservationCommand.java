package hhplus.concert.application.dto;

import lombok.Builder;

@Builder
public record ReservationCommand(
        String token,
        Long userId,
        Long concertId,
        Long scheduleId,
        Long seatId
) {
}

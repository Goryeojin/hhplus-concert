package hhplus.concert.domain.model;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ConcertSchedule(
    Long id,
    Long concertId,
    LocalDateTime reservationAt,
    LocalDateTime deadline,
    LocalDateTime concertAt
) {
}

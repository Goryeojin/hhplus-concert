package hhplus.concert.application.dto;

import hhplus.concert.domain.model.Seat;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record SeatsResult(
        Long scheduleId,
        Long concertId,
        LocalDateTime concertAt,
        List<Seat> seats
) {
}

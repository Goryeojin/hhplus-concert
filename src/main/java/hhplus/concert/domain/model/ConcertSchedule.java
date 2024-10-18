package hhplus.concert.domain.model;

import hhplus.concert.support.exception.CustomException;
import hhplus.concert.support.exception.ErrorCode;
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
    public void checkStatus() {
        if (reservationAt().isAfter(LocalDateTime.now())) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        if (deadline().isBefore(LocalDateTime.now())) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}

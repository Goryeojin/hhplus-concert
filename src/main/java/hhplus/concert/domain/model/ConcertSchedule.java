package hhplus.concert.domain.model;

import hhplus.concert.support.exception.CustomException;
import hhplus.concert.support.code.ErrorCode;
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
            throw new CustomException(ErrorCode.BEFORE_RESERVATION_AT);
        }
        if (deadline().isBefore(LocalDateTime.now())) {
            throw new CustomException(ErrorCode.AFTER_DEADLINE);
        }
    }
}

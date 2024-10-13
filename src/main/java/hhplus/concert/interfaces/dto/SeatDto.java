package hhplus.concert.interfaces.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import hhplus.concert.support.type.SeatStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

public class SeatDto {

    @Builder
    public record Response (
        Long concertId,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime concertAt,
        Long maxSeats,
        List<?> seats
    ) {

        @Builder
        public record SeatInfo (
            Long seatId,
            Long seatNumber,
            SeatStatus seatStatus,
            Long seatPrice
        ) {
        }
    }
}

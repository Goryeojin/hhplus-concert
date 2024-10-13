package hhplus.concert.interfaces.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import hhplus.concert.support.type.ReservationStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

public class ReservationDto {

    @Builder
    public record Request (
        Long userId,
        Long concertId,
        Long scheduleId,
        List<Integer> seatIds
    ) {
    }

    @Builder
    public record Response (
        Long reservationId,
        Long concertId,
        String concertName,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime concertAt,
        List<SeatDto.Response.SeatInfo> seats,
        Long totalPrice,
        ReservationStatus reservationStatus
    ) {
    }
}

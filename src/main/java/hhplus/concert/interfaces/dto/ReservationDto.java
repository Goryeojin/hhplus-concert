package hhplus.concert.interfaces.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import hhplus.concert.support.type.ReservationStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

public class ReservationDto {

    @Builder
    public static class Request {
        private Long userId;
        private Long concertId;
        private Long scheduleId;
        private List<Integer> seatIds;
    }

    @Getter
    @Builder
    public static class Response {
        private Long reservationId;
        private Long concertId;
        private String concertName;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime concertAt;
        private List<SeatDto.Response.SeatInfo> seats;
        private Long totalPrice;
        private ReservationStatus reservationStatus;
    }
}

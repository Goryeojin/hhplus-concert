package hhplus.concert.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import hhplus.concert.support.type.SeatStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

public class SeatDto {

    @Getter
    @Builder
    public static class Response {
        private Long concertId;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime concertAt;
        private Long maxSeats;
        private List<?> seats;

        @Getter
        @Builder
        public static class SeatInfo {
            private Long seatId;
            private Long seatNumber;
            private SeatStatus seatStatus;
            private Long seatPrice;
        }
    }
}

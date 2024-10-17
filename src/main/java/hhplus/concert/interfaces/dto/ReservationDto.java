package hhplus.concert.interfaces.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import hhplus.concert.application.dto.ReservationCommand;
import hhplus.concert.support.type.ReservationStatus;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public class ReservationDto {

    @Builder
    public record Request (
        Long userId,
        Long concertId,
        Long scheduleId,
        Long seatId
    ) {
        public ReservationCommand toCommand(String token) {
            return ReservationCommand.builder()
                    .token(token)
                    .userId(this.userId)
                    .concertId(this.concertId)
                    .scheduleId(this.scheduleId)
                    .seatId(this.seatId)
                    .build();
        }
    }

    @Builder
    public record Response (
        Long reservationId,
        Long concertId,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime concertAt,
        SeatDto seat,
        ReservationStatus reservationStatus
    ) {
    }
}

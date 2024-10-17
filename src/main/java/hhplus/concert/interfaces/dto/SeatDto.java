package hhplus.concert.interfaces.dto;

import hhplus.concert.support.type.SeatStatus;
import lombok.Builder;

@Builder
public record SeatDto (
        Long seatId,
        int seatNo,
        SeatStatus seatStatus,
        int seatPrice
) {
}
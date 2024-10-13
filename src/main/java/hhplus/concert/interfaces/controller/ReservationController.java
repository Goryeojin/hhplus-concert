package hhplus.concert.interfaces.controller;

import hhplus.concert.interfaces.dto.ReservationDto;
import hhplus.concert.interfaces.dto.SeatDto;
import hhplus.concert.support.type.ReservationStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reservations")
public class ReservationController {

    /**
     * 콘서트 좌석을 예약한다.
     * @param token 발급받은 토큰
     * @param request userId, concertId, scheduleId, seats
     * @return 예약 결과 dto
     */
    @PostMapping("/reservations")
    public ResponseEntity<ReservationDto.Response> createReservation(
            @RequestHeader("TOKEN") String token,
            @RequestBody ReservationDto.Request request
    ) {
        return ResponseEntity.ok(
                ReservationDto.Response.builder()
                        .reservationId(1L)
                        .concertId(1L)
                        .concertName("콘서트 이름")
                        .concertAt(LocalDateTime.now())
                        .seats(List.of(
                                SeatDto.Response.SeatInfo.builder()
                                        .seatNumber(1L)
                                        .seatPrice(10000L)
                                        .build(),
                                SeatDto.Response.SeatInfo.builder()
                                        .seatNumber(2L)
                                        .seatPrice(10000L)
                                        .build()
                        ))
                        .totalPrice(20000L)
                        .reservationStatus(ReservationStatus.PAYMENT_WAITING)
                        .build()
        );
    }
}

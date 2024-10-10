package hhplus.concert.interfaces.controller;

import hhplus.concert.interfaces.dto.*;
import hhplus.concert.support.type.PaymentStatus;
import hhplus.concert.support.type.QueueStatus;
import hhplus.concert.support.type.ReservationStatus;
import hhplus.concert.support.type.SeatStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/mock")
public class MockApiController {
    // 1. 대기열 큐에 사용자를 추가하고 토큰을 발급한다.
    @PostMapping("/queue/tokens")
    public ResponseEntity<TokenDto.Response> createQueueToken(@RequestBody TokenDto.Request request) {
        return ResponseEntity.ok(
                TokenDto.Response.builder()
                        .token("a1b2c3d4")
                        .createdAt(LocalDateTime.now())
                        .expiredAt(LocalDateTime.now().plusMinutes(10L)).build()
        );
    }

    // 2. 토큰을 조회한다.
    @GetMapping("/queue/tokens")
    public ResponseEntity<TokenDto.Response> getToken(@RequestParam Long userId) {
        return ResponseEntity.ok(
                TokenDto.Response.builder()
                        .token("a1b2c3d4")
                        .createdAt(LocalDateTime.now())
                        .expiredAt(LocalDateTime.now().plusMinutes(10L)).build()
        );
    }

    // 3. 대기 상태를 조회한다.
    @GetMapping("/queue/status")
    public ResponseEntity<QueueDto.Response> getQueueStatus(
            @RequestHeader("TOKEN") String token,
            @RequestParam Long userId
    ) {
        return ResponseEntity.ok(
                QueueDto.Response.builder()
                        .createdAt(LocalDateTime.now())
                        .status(QueueStatus.WAITING)
                        .remainingQueueCount(10L).build()
        );
    }

    // 4. 콘서트 예약 가능 일정 조회
    @GetMapping("/concerts/{concertId}/schedule")
    public ResponseEntity<ScheduleDto.Response> getConcertSchedule(
            @RequestHeader("TOKEN") String token,
            @PathVariable Long concertId
    ) {
        return ResponseEntity.ok(
                ScheduleDto.Response.builder()
                        .concertId(1L)
                        .schedule(List.of(
                                ScheduleDto.Response.ScheduleInfo.builder()
                                        .scheduleId(1L)
                                        .concertAt(LocalDateTime.now())
                                        .reservationAt(LocalDateTime.now())
                                        .build(),
                                ScheduleDto.Response.ScheduleInfo.builder()
                                        .scheduleId(2L)
                                        .concertAt(LocalDateTime.now())
                                        .reservationAt(LocalDateTime.now())
                                        .build()
                        )).build()
        );
    }

    // 5. 콘서트 예약 가능 좌석 조회
    @GetMapping("/concerts/{concertId}/schedule/{scheduleId}/seats")
    public ResponseEntity<SeatDto.Response> getSeats(
            @RequestHeader("TOKEN") String token,
            @PathVariable Long concertId,
            @PathVariable Long scheduleId
    ) {
        return ResponseEntity.ok(
                SeatDto.Response.builder()
                        .concertId(1L)
                        .concertAt(LocalDateTime.now())
                        .maxSeats(50L)
                        .seats(List.of(
                                SeatDto.Response.SeatInfo.builder()
                                        .seatId(1L)
                                        .seatNumber(1L)
                                        .seatStatus(SeatStatus.AVAILABLE)
                                        .seatPrice(10000L)
                                        .build(),
                                SeatDto.Response.SeatInfo.builder()
                                        .seatId(1L)
                                        .seatNumber(1L)
                                        .seatStatus(SeatStatus.AVAILABLE)
                                        .seatPrice(10000L)
                                        .build()
                        )).build()
        );
    }

    // 6. 좌석 예약
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

    // 7. 결제 요청
    @PostMapping("/payments")
    public ResponseEntity<PaymentDto.Response> proceedPayment(
            @RequestHeader("TOKEN") String token,
            @RequestBody PaymentDto.Request request
    ) {
        return ResponseEntity.ok(
                PaymentDto.Response.builder()
                        .paymentId(1L)
                        .amount(30000L)
                        .paymentStatus(PaymentStatus.COMPLETED).build()
        );
    }

    // 8. 잔액 조회
    @GetMapping("/users/{userId}/balance")
    public ResponseEntity<BalanceDto.Response> getBalance(@PathVariable Long userId) {
        return ResponseEntity.ok(
                BalanceDto.Response.builder()
                        .userId(1L)
                        .currentAmount(50000L).build()
        );
    }

    // 9. 잔액 충전
    @PatchMapping("/users/{userId}/balance")
    public ResponseEntity<BalanceDto.Response> chargeBalance(@PathVariable Long userId) {
        return ResponseEntity.ok(
                BalanceDto.Response.builder()
                        .userId(1L)
                        .currentAmount(50000L).build()
        );
    }
}

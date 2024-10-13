package hhplus.concert.interfaces.controller;

import hhplus.concert.interfaces.dto.ConcertDto;
import hhplus.concert.interfaces.dto.ScheduleDto;
import hhplus.concert.interfaces.dto.SeatDto;
import hhplus.concert.support.type.SeatStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/concerts")
public class ConcertController {

    /**
     * 예약 가능한 콘서트 목록을 조회한다.
     * @param token 발급받은 토큰
     * @return 콘서트 dto
     */
    @GetMapping
    public ResponseEntity<ConcertDto.Response> getAvailableConcert(@RequestHeader("Token") String token) {
        return ResponseEntity.ok(
                ConcertDto.Response.builder()
                        .concerts(List.of(
                                ConcertDto.Response.ConcertInfo.builder()
                                        .concertId(1L)
                                        .title("1번 콘서트")
                                        .description("1번 콘서트 입니다.")
                                        .build()
                        )).build()
        );
    }

    /**
     * 특정 콘서트의 예약 가능한 일정을 조회한다.
     * @param token 발급받은 토큰
     * @param concertId 콘서트 ID
     * @return 콘서트 일정 dto
     */
    @GetMapping("/{concertId}/schedules")
    public ResponseEntity<ScheduleDto.Response> getConcertSchedule(
            @RequestHeader("Token") String token,
            @PathVariable Long concertId
    ) {
        return ResponseEntity.ok(
                ScheduleDto.Response.builder()
                        .concertId(1L)
                        .schedules(List.of(
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

    /**
     * 특정 콘서트 일정의 예약 가능한 좌석을 조회한다.
     * @param token 발급받은 토큰
     * @param concertId 콘서트 ID
     * @param scheduleId 일정 ID
     * @return 일정별 좌석 dto
     */
    @GetMapping("/{concertId}/schedules/{scheduleId}/seats")
    public ResponseEntity<SeatDto.Response> getSeats(
            @RequestHeader("Token") String token,
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
}

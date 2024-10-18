package hhplus.concert.interfaces.controller;

import hhplus.concert.application.dto.SeatsResponse;
import hhplus.concert.application.facade.ConcertFacade;
import hhplus.concert.domain.model.Concert;
import hhplus.concert.domain.model.ConcertSchedule;
import hhplus.concert.interfaces.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/concerts")
@RequiredArgsConstructor
public class ConcertController {

    private final ConcertFacade concertFacade;

    /**
     * 콘서트 목록을 조회한다.
     * @param token 발급받은 토큰
     * @return 콘서트 dto
     */
    @GetMapping
    public ResponseEntity<GetConcertDto.ConcertResponse> getConcerts(@RequestHeader("Token") String token) {
        List<Concert> concerts = concertFacade.getConcerts(token);
        List<ConcertDto> concertDtos = concerts.stream()
                .map(concert -> ConcertDto.builder()
                        .concertId(concert.id())
                        .title(concert.title())
                        .description(concert.description())
                        .status(concert.status())
                        .build())
                .toList();
        return ResponseEntity.ok(
                GetConcertDto.ConcertResponse.builder()
                        .concerts(concertDtos)
                        .build()
        );
    }

    /**
     * 특정 콘서트의 예약 가능한 일정을 조회한다.
     * @param token 발급받은 토큰
     * @param concertId 콘서트 ID
     * @return 콘서트 일정 dto
     */
    @GetMapping("/{concertId}/schedules")
    public ResponseEntity<GetScheduleDto.ScheduleResponse> getConcertSchedules(
            @RequestHeader("Token") String token,
            @PathVariable Long concertId
    ) {
        List<ConcertSchedule> schedules = concertFacade.getConcertSchedules(token, concertId);
        List<ScheduleDto> scheduleDtos = (schedules != null) ? schedules.stream()
                .map(schedule -> ScheduleDto.builder()
                        .scheduleId(schedule.id())
                        .concertAt(schedule.concertAt())
                        .reservationAt(schedule.reservationAt())
                        .deadline(schedule.deadline())
                        .build())
                .toList() : Collections.emptyList();
        return ResponseEntity.ok(
                GetScheduleDto.ScheduleResponse.builder()
                        .concertId(concertId)
                        .schedules(scheduleDtos)
                        .build()
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
    public ResponseEntity<GetSeatDto.SeatResponse> getSeats(
            @RequestHeader("Token") String token,
            @PathVariable Long concertId,
            @PathVariable Long scheduleId
    ) {
        SeatsResponse seats = concertFacade.getSeats(token, concertId, scheduleId);
        List<SeatDto> list = (seats.seats() != null) ? seats.seats().stream()
                .map(seat -> SeatDto.builder()
                        .seatId(seat.id())
                        .seatNo(seat.seatNo())
                        .seatStatus(seat.status())
                        .seatPrice(seat.seatPrice())
                        .build())
                .toList() : Collections.emptyList();

        return ResponseEntity.ok(
                GetSeatDto.SeatResponse.builder()
                        .scheduleId(seats.scheduleId())
                        .concertId(seats.concertId())
                        .concertAt(seats.concertAt())
                        .maxSeats(50L)
                        .seats(list)
                        .build()
        );
    }
}

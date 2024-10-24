package hhplus.concert.infra.repository.impl;

import hhplus.concert.domain.model.Concert;
import hhplus.concert.domain.model.ConcertSchedule;
import hhplus.concert.domain.model.Seat;
import hhplus.concert.domain.repository.ConcertRepository;
import hhplus.concert.infra.entity.SeatEntity;
import hhplus.concert.infra.repository.jpa.ConcertJpaRepository;
import hhplus.concert.infra.repository.jpa.ConcertScheduleJpaRepository;
import hhplus.concert.infra.repository.jpa.SeatJpaRepository;
import hhplus.concert.support.code.ErrorCode;
import hhplus.concert.support.exception.CoreException;
import hhplus.concert.support.type.SeatStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ConcertRepositoryImpl implements ConcertRepository {

    private final ConcertJpaRepository concertJpaRepository;
    private final ConcertScheduleJpaRepository concertScheduleJpaRepository;
    private final SeatJpaRepository seatJpaRepository;

    @Override
    public List<Concert> findConcerts() {
        return concertJpaRepository.findAll().stream()
                .map(entity -> entity.of(entity))
                .collect(Collectors.toList());
    }

    @Override
    public List<ConcertSchedule> findConcertSchedules(Long concertId) {
        LocalDateTime now = LocalDateTime.now();
        return concertScheduleJpaRepository.findByConcertIdAndReservationAtBeforeAndDeadlineAfter(concertId, now, now).stream()
                        .map(entity -> entity.of(entity))
                        .toList();
    }

    @Override
    public Concert findConcert(Long concertId) {
        return concertJpaRepository.findById(concertId)
                .map(entity -> entity.of(entity))
                .orElseThrow(() -> new CoreException(ErrorCode.CONCERT_NOT_FOUND));
    }

    @Override
    public List<Seat> findSeats(Long concertId, Long scheduleId, SeatStatus seatStatus) {
        return seatJpaRepository.findSeats(concertId, scheduleId, seatStatus).stream()
                .map(entity -> entity.of(entity))
                .toList();
    }

    @Override
    public ConcertSchedule findConcertSchedule(Long scheduleId) {
        return concertScheduleJpaRepository.findById(scheduleId)
                .map(entity -> entity.of(entity))
                .orElseThrow(() -> new CoreException(ErrorCode.SCHEDULE_NOT_FOUND));
    }

    @Override
    public void saveSeat(Seat seat) {
        seatJpaRepository.save(SeatEntity.from(seat));
    }

    @Override
    public Seat findSeat(Long seatId) {
        return seatJpaRepository.findBySeatId(seatId)
                .map(entity -> entity.of(entity))
                .orElseThrow(() -> new CoreException(ErrorCode.SEAT_NOT_FOUND));
    }
}

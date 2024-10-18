package hhplus.concert.infra.repository.impl;

import hhplus.concert.domain.model.Concert;
import hhplus.concert.domain.model.ConcertSchedule;
import hhplus.concert.domain.model.Seat;
import hhplus.concert.domain.repository.ConcertRepository;
import hhplus.concert.infra.entity.ConcertEntity;
import hhplus.concert.infra.entity.ConcertScheduleEntity;
import hhplus.concert.infra.entity.SeatEntity;
import hhplus.concert.infra.repository.jpa.ConcertJpaRepository;
import hhplus.concert.infra.repository.jpa.ConcertScheduleJpaRepository;
import hhplus.concert.infra.repository.jpa.SeatJpaRepository;
import hhplus.concert.support.exception.CustomException;
import hhplus.concert.support.exception.ErrorCode;
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
                .map(ConcertEntity::of)
                .collect(Collectors.toList());
    }

    @Override
    public List<ConcertSchedule> findConcertSchedules(Long concertId) {
        LocalDateTime now = LocalDateTime.now();
        return concertScheduleJpaRepository.findByConcertIdAndReservationAtBeforeAndDeadlineAfter(concertId, now, now).stream()
                        .map(ConcertScheduleEntity::of)
                        .toList();
    }

    @Override
    public Concert findConcert(Long concertId) {
        return concertJpaRepository.findById(concertId)
                .map(ConcertEntity::of)
                .orElseThrow(() -> new CustomException(ErrorCode.CONCERT_NOT_FOUND));
    }

    @Override
    public List<Seat> findSeats(Long concertId, Long scheduleId, SeatStatus seatStatus) {
        return seatJpaRepository.findSeats(concertId, scheduleId, seatStatus).stream()
                .map(SeatEntity::of)
                .toList();
    }

    @Override
    public ConcertSchedule findConcertSchedule(Long scheduleId) {
        return concertScheduleJpaRepository.findById(scheduleId)
                .map(ConcertScheduleEntity::of)
                .orElseThrow(() -> new CustomException(ErrorCode.SCHEDULE_NOT_FOUND));
    }

    @Override
    public void saveConcert(Concert concert) {
        concertJpaRepository.save(ConcertEntity.from(concert));
    }

    @Override
    public void saveSchedule(ConcertSchedule schedule) {
        concertScheduleJpaRepository.save(ConcertScheduleEntity.from(schedule));
    }

    @Override
    public void saveSeat(Seat seat) {
        seatJpaRepository.save(SeatEntity.from(seat));
    }

    @Override
    public Seat findSeat(Long seatId) {
        return seatJpaRepository.findById(seatId)
                .map(SeatEntity::of)
                .orElseThrow(() -> new CustomException(ErrorCode.SEAT_NOT_FOUND));
    }
}

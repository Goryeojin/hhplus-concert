package hhplus.concert.domain.repository;

import hhplus.concert.domain.model.Concert;
import hhplus.concert.domain.model.ConcertSchedule;
import hhplus.concert.domain.model.Seat;
import hhplus.concert.support.type.SeatStatus;

import java.util.List;

public interface ConcertRepository {
    List<Concert> findConcerts();

    List<ConcertSchedule> findConcertSchedules(Long concertId);

    Concert findConcert(Long concertId);

    List<Seat> findSeats(Long concertId, Long scheduleId, SeatStatus seatStatus);

    ConcertSchedule findConcertSchedule(Long scheduleId);

    void saveConcert(Concert concert);

    void saveSchedule(ConcertSchedule schedule);

    void saveSeat(Seat seat);

    Seat findSeat(Long seatId);
}

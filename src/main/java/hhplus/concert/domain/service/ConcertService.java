package hhplus.concert.domain.service;

import hhplus.concert.domain.model.Concert;
import hhplus.concert.domain.model.ConcertSchedule;
import hhplus.concert.domain.model.Seat;
import hhplus.concert.domain.repository.ConcertRepository;
import hhplus.concert.support.type.SeatStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConcertService {

    private final ConcertRepository concertRepository;

    public List<Concert> getConcerts() {
        return concertRepository.findConcerts();
    }

    public List<ConcertSchedule> getConcertSchedules(Long concertId) {
        Concert concert = concertRepository.findConcert(concertId);
        // 해당 콘서트가 예약 가능한지 확인
        if (!concert.checkStatus()) {
            return null;
        }
        return concertRepository.findConcertSchedules(concertId);
    }

    public List<Seat> getSeats(Long concertId, Long scheduleId) {
        return concertRepository.findSeats(concertId, scheduleId, SeatStatus.AVAILABLE);
    }

    public ConcertSchedule scheduleInfo(Long scheduleId) {
        return concertRepository.findConcertSchedule(scheduleId);
    }
}

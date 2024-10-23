package hhplus.concert.application.facade;

import hhplus.concert.application.dto.SeatsResult;
import hhplus.concert.domain.model.Concert;
import hhplus.concert.domain.model.ConcertSchedule;
import hhplus.concert.domain.model.Seat;
import hhplus.concert.domain.service.ConcertService;
import hhplus.concert.domain.service.QueueService;
import hhplus.concert.support.type.SeatStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ConcertFacade {

    private final ConcertService concertService;
    private final QueueService queueService;

    public List<Concert> getConcerts(String token) {
        return concertService.getConcerts();
    }

    public List<ConcertSchedule> getConcertSchedules(String token, Long concertId) {
        Concert concert = concertService.getConcert(concertId);
        return concertService.getConcertSchedules(concert);
    }

    public SeatsResult getSeats(String token, Long concertId, Long scheduleId) {
        Concert concert = concertService.getConcert(concertId);
        ConcertSchedule schedule = concertService.scheduleInfo(scheduleId);
        List<Seat> seats = concertService.getSeats(concert.id(), schedule.id(), SeatStatus.AVAILABLE);

        return SeatsResult.builder()
                .scheduleId(schedule.id())
                .concertId(schedule.concertId())
                .concertAt(schedule.concertAt())
                .seats(seats)
                .build();
    }
}

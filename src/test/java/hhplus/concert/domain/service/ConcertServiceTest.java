package hhplus.concert.domain.service;

import hhplus.concert.domain.model.Concert;
import hhplus.concert.domain.model.ConcertSchedule;
import hhplus.concert.domain.model.Seat;
import hhplus.concert.domain.repository.ConcertRepository;
import hhplus.concert.support.type.ConcertStatus;
import hhplus.concert.support.type.SeatStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ConcertServiceTest {

    @Mock
    private ConcertRepository concertRepository;

    @InjectMocks
    private ConcertService concertService;

    private Concert concert;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        concert = Concert.builder()
                .id(1L)
                .title("Test Title")
                .description("Test Description")
                .status(ConcertStatus.AVAILABLE)
                .build();
    }

    @Test
    void 예약_가능한_콘서트_조회() {
        // given
        when(concertRepository.findConcerts()).thenReturn(Arrays.asList(concert));

        // when
        List<Concert> concerts = concertService.getConcerts();

        // then
        assertThat(concerts).hasSize(1);
        assertThat(concerts.get(0).id()).isEqualTo(concert.id());
    }

    @Test
    void 예약_가능한_콘서트_스케줄_조회() {
        // given
        ConcertSchedule schedule = ConcertSchedule.builder()
                .id(1L)
                .concertId(concert.id())
                .reservationAt(LocalDateTime.now().minusDays(1))
                .deadline(LocalDateTime.now().plusDays(1))
                .concertAt(LocalDateTime.now().plusDays(5))
                .build();

        when(concertRepository.findConcert(concert.id())).thenReturn(concert);
        when(concertRepository.findConcertSchedules(concert.id())).thenReturn(Arrays.asList(schedule));

        // when
        List<ConcertSchedule> schedules = concertService.getConcertSchedules(concert);

        // then
        assertThat(schedules).hasSize(1);
        assertThat(schedules.get(0).concertId()).isEqualTo(concert.id());
    }

    @Test
    void 예약_가능한_좌석_조회() {
        // given
        Seat seat = Seat.builder()
                .id(1L)
                .concertScheduleId(1L)
                .seatNo(1)
                .seatPrice(1000)
                .status(SeatStatus.AVAILABLE)
                .build();

        when(concertRepository.findSeats(concert.id(), 1L, SeatStatus.AVAILABLE)).thenReturn(Arrays.asList(seat));

        // when
        List<Seat> seats = concertService.getSeats(concert.id(), 1L);

        // then
        assertThat(seats).hasSize(1);
        assertThat(seats.get(0).concertScheduleId()).isEqualTo(seat.concertScheduleId());
    }

    @Test
    void 스케줄_정보_조회() {
        // given
        ConcertSchedule schedule = ConcertSchedule.builder()
                .id(1L)
                .concertId(concert.id())
                .reservationAt(LocalDateTime.now().minusDays(1))
                .deadline(LocalDateTime.now().plusDays(1))
                .concertAt(LocalDateTime.now().plusDays(5))
                .build();
        when(concertRepository.findConcertSchedule(1L)).thenReturn(schedule);

        // when
        ConcertSchedule fetchedSchedule = concertService.scheduleInfo(1L);

        // then
        assertThat(fetchedSchedule).isNotNull();
        assertThat(fetchedSchedule.id()).isEqualTo(1L);
    }
}

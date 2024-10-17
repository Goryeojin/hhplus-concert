package hhplus.concert.application.facade;

import hhplus.concert.application.dto.SeatsResponse;
import hhplus.concert.domain.model.Concert;
import hhplus.concert.domain.model.ConcertSchedule;
import hhplus.concert.domain.model.Queue;
import hhplus.concert.domain.model.Seat;
import hhplus.concert.domain.repository.ConcertRepository;
import hhplus.concert.domain.service.QueueService;
import hhplus.concert.support.type.ConcertStatus;
import hhplus.concert.support.type.SeatStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
class ConcertFacadeIntegrationTest {

    @Autowired
    private QueueFacade queueFacade;

    @Autowired
    private ConcertFacade concertFacade;

    @Autowired
    private ConcertRepository concertRepository;

    private Concert concert1;
    private Concert concert2;
    private String token;

    @BeforeEach
    void setUp() {
        Queue queue = queueFacade.createToken(1L);
        token = queue.token(); // 토큰 검증 통과를 위한 토큰 생성

        concert1 = new Concert(1L, "Test Concert", "Test Description", ConcertStatus.AVAILABLE);
        concert2 = new Concert(2L, "Test Concert", "Test Description", ConcertStatus.UNAVAILABLE);
        concertRepository.saveConcert(concert1);
        concertRepository.saveConcert(concert2);
    }

    @Test
    @Transactional
    void 콘서트_조회() {
        // when
        List<Concert> concerts = concertFacade.getConcerts(token);

        // then
        assertThat(concerts).hasSize(2);
        assertThat(concerts.get(0).id()).isEqualTo(concert1.id());
        assertThat(concerts.get(1).id()).isEqualTo(concert2.id());
    }

    @Test
    void 예약_가능한_콘서트_스케줄_조회() {
        // given
        ConcertSchedule schedule1 = new ConcertSchedule(1L, concert1.id(), LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(5));
        ConcertSchedule schedule2 = new ConcertSchedule(2L, concert1.id(), LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2), LocalDateTime.now().plusDays(10));
        ConcertSchedule schedule3 = new ConcertSchedule(3L, concert1.id(), LocalDateTime.now().minusDays(2), LocalDateTime.now().minusDays(1), LocalDateTime.now());
        concertRepository.saveSchedule(schedule1); // 예약 가능한 시간인 일정
        concertRepository.saveSchedule(schedule2); // 아직 예약 불가능한 일정
        concertRepository.saveSchedule(schedule3); // 예약 가능 시간이 지난 일정

        // when
        List<ConcertSchedule> schedules = concertFacade.getConcertSchedules(token, concert1.id());

        // then
        assertThat(schedules).hasSize(1); // 예약 가능한 시간인 일정만 조회한다.
        assertThat(schedules.get(0).concertId()).isEqualTo(concert1.id());
    }

    @Test
    void 예약_가능한_좌석_조회() {
        // given
        ConcertSchedule schedule = new ConcertSchedule(1L, concert1.id(), LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(5));
        concertRepository.saveSchedule(schedule);

        Seat seat1 = new Seat(1L, schedule.id(), 1, SeatStatus.AVAILABLE, null, 10000); // 예약 가능
        Seat seat2 = new Seat(2L, schedule.id(), 2, SeatStatus.UNAVAILABLE, null, 10000); // 예약 불가능
        concertRepository.saveSeat(seat1);
        concertRepository.saveSeat(seat2);

        // when
        SeatsResponse response = concertFacade.getSeats(token, concert1.id(), schedule.id());

        // then
        assertThat(response).isNotNull();
        assertThat(response.seats()).hasSize(1); // 예약 가능한 좌석만 조회
    }
}

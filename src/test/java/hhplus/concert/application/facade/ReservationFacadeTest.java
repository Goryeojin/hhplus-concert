package hhplus.concert.application.facade;

import hhplus.concert.application.dto.ReservationCommand;
import hhplus.concert.application.dto.ReservationResponse;
import hhplus.concert.domain.model.Concert;
import hhplus.concert.domain.model.ConcertSchedule;
import hhplus.concert.domain.model.Queue;
import hhplus.concert.domain.model.Seat;
import hhplus.concert.domain.repository.ConcertRepository;
import hhplus.concert.domain.repository.QueueRepository;
import hhplus.concert.domain.repository.ReservationRepository;
import hhplus.concert.domain.service.ConcertService;
import hhplus.concert.domain.service.QueueService;
import hhplus.concert.domain.service.ReservationService;
import hhplus.concert.support.exception.CustomException;
import hhplus.concert.support.exception.ErrorCode;
import hhplus.concert.support.type.ConcertStatus;
import hhplus.concert.support.type.ReservationStatus;
import hhplus.concert.support.type.SeatStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class ReservationFacadeTest {
    @Autowired
    private ReservationFacade reservationFacade;

    @Autowired
    private QueueService queueService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ConcertService concertService;

    @Autowired
    private ConcertRepository concertRepository;

    @Autowired
    private QueueRepository queueRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    private Concert concert;
    private ConcertSchedule schedule;
    private Seat seat;

    @BeforeEach
    void setUp() {
        // 콘서트 스케줄 생성
        concert = new Concert(1L, "Test", "Test", ConcertStatus.AVAILABLE);
        schedule = new ConcertSchedule(1L, 1L, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(5), LocalDateTime.now().plusDays(2));
        concertRepository.saveSchedule(schedule);

        // 좌석 생성
        seat = new Seat(1L, schedule.id(), 1, SeatStatus.AVAILABLE, null, 10000);
        Seat seat2 = new Seat(2L, schedule.id(), 1, SeatStatus.UNAVAILABLE, null, 10000);
        concertRepository.saveSeat(seat);
        concertRepository.saveSeat(seat2);
    }

    @Test
    void 예약_성공() {
        // given
        Long userId = 1L;
        queueService.createToken(userId);
        Queue token = queueService.getToken(userId);

        ReservationCommand command = new ReservationCommand(token.token(), userId, concert.id(), schedule.id(), seat.id());

        // when
        ReservationResponse response = reservationFacade.reservation(command);

        // then
        assertThat(response).isNotNull();
        assertThat(response.reservationId()).isNotNull();
        assertThat(response.concertId()).isEqualTo(schedule.concertId());
        assertThat(response.seat().id()).isEqualTo(seat.id());
        assertThat(response.seat().seatNo()).isEqualTo(seat.seatNo());
        assertThat(response.status()).isEqualTo(ReservationStatus.PAYMENT_WAITING);

        // 예약 정보가 실제 저장됐는지 확인
        assertThat(reservationRepository.findById(response.reservationId())).isNotNull();
    }

    @Test
    void 예약_실패_좌석_상태_확인() {
        // given
        Long userId = 1L;
        queueService.createToken(userId);
        Queue token = queueService.getToken(userId);

        ReservationCommand command = new ReservationCommand(token.token(), userId, concert.id(), schedule.id(), 2L);

        // when & then
        // 좌석이 예약 불가 상태이므로 예외 발생 기대
        assertThatThrownBy(() -> reservationFacade.reservation(command))
                .isInstanceOf(CustomException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.SEAT_UNAVAILABLE);
    }
}
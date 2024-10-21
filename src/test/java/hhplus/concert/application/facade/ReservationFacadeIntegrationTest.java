package hhplus.concert.application.facade;

import hhplus.concert.application.dto.ReservationCommand;
import hhplus.concert.application.dto.ReservationResult;
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
import hhplus.concert.support.type.ReservationStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class ReservationFacadeIntegrationTest {
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

    private String token;
    private final Long USER_ID = 1L;

    @BeforeEach
    void setUp() {
        Queue queue = queueService.createToken(USER_ID);
        token = queue.token(); // 토큰 검증 통과를 위한 토큰 생성
    }

    @Test
    void 예약_가능_시간_이전에_예약_요청_시_BEFORE_RESERVATION_AT_에러를_반환한다() {
        // given
        ConcertSchedule beforeReservationAtSchedule =
                concertRepository.findConcertSchedule(3L);
        ReservationCommand command = new ReservationCommand(token, USER_ID, 1L, beforeReservationAtSchedule.id(), 1L);

        // when & then
        assertThatThrownBy(() -> reservationFacade.reservation(command))
                .isInstanceOf(CustomException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.BEFORE_RESERVATION_AT);

    }
    
    @Test
    void 예약_마감_시간_이후에_예약_요청_시_AFTER_DEADLINE_에러를_반환한다() {
        // given
        ConcertSchedule afterDeadlineSchedule =
                concertRepository.findConcertSchedule(4L);
        ReservationCommand command = new ReservationCommand(token, USER_ID, 1L, afterDeadlineSchedule.id(), 1L);

        // when & then
        assertThatThrownBy(() -> reservationFacade.reservation(command))
                .isInstanceOf(CustomException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.AFTER_DEADLINE);
    }
    
    @Test
    void 좌석의_상태가_UNAVAILABLE_이라면_SEAT_UNAVAILABLE_에러를_반환한다() {
        // given
        ConcertSchedule schedule = concertRepository.findConcertSchedule(1L); // 예약 가능한 콘서트 일정
        Seat seat = concertRepository.findSeat(26L); // 상태가 UNAVAILABLE 인 좌석
        ReservationCommand command = new ReservationCommand(token, USER_ID, 1L, schedule.id(), seat.id());

        // when & then
        assertThatThrownBy(() -> reservationFacade.reservation(command))
                .isInstanceOf(CustomException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.SEAT_UNAVAILABLE);
    }
    
    @Test
    void 예약_가능한_시간이고_좌석이_예약_가능_상태라면_예약_정보를_생성하고_반환한다() {
        // given
        ConcertSchedule schedule = concertRepository.findConcertSchedule(1L); // 예약 가능한 콘서트 일정
        Seat seat = concertRepository.findSeat(1L); // 상태가 AVAILABLE 인 좌석
        ReservationCommand command = new ReservationCommand(token, USER_ID, 1L, schedule.id(), seat.id());

        // when
        ReservationResult reservation = reservationFacade.reservation(command);

        // then
        assertThat(reservation.status()).isEqualTo(ReservationStatus.PAYMENT_WAITING); // 결제 대기 상태로 변경되었는지 검증
    }
}
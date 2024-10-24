package hhplus.concert.application.facade;

import hhplus.concert.domain.model.*;
import hhplus.concert.domain.repository.*;
import hhplus.concert.domain.service.*;
import hhplus.concert.support.exception.CoreException;
import hhplus.concert.support.code.ErrorCode;
import hhplus.concert.support.type.ReservationStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class PaymentFacadeIntegrationTest {

    @Autowired
    private PaymentFacade paymentFacade;

    @Autowired
    private QueueService queueService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private PointService pointService;

    @Autowired
    private ConcertService concertService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private QueueRepository queueRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PointRepository pointRepository;

    @Autowired
    private ConcertRepository concertRepository;

    private final Long USER_ID = 1L;
    private Reservation reservation;
    private String token;

    @BeforeEach
    void setUp() {
        Queue queue = queueService.createToken(USER_ID);
        token = queue.token(); // 토큰 검증 통과를 위한 토큰 생성

        reservation = new Reservation(1L, 1L, 1L, 1L, USER_ID, ReservationStatus.PAYMENT_WAITING, LocalDateTime.now());
        reservationRepository.save(reservation); // 테스트에서 사용할 예약을 저장한다.
    }

    @Test
    @Transactional
    void 잔액이_충분하다면_결제에_성공한다() {
        // given
        Point point = pointService.getPoint(USER_ID);
        pointService.chargePoint(USER_ID, 10_000L);

        // when
        Payment payment = paymentFacade.payment(token, reservation.id(), USER_ID);

        // then
        assertThat(payment).isNotNull();
        assertThat(payment.userId()).isEqualTo(USER_ID);
        assertThat(payment.reservationId()).isEqualTo(reservation.id());

        Reservation updatedReservation = reservationRepository.findById(reservation.id());
        assertThat(updatedReservation.status()).isEqualTo(ReservationStatus.COMPLETED); // 예약이 완료 상태로 변경되었는지 검증

        Point userPoint = pointService.getPoint(USER_ID);
        Seat reservedSeat = concertService.getSeat(updatedReservation.seatId());
        assertThat(userPoint.amount()).isEqualTo(0); // 잔액 차감 확인
    }

    @Test
    void 잔액이_부족할_경우_PAYMENT_FAILED_AMOUNT_에러를_반환한다() {
        // when & then
        // 잔액을 충전하지 않을 경우 잔액은 0이기 때문에 결제에 실패한다.
        assertThatThrownBy(() -> paymentFacade.payment(token, reservation.id(), USER_ID))
                .isInstanceOf(CoreException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.PAYMENT_FAILED_AMOUNT);
    }

    @Test
    void 예약자와_결제자_정보가_상이할_경우_PAYMENT_DIFFERENT_USER_에러를_반환한다() {
        // when & then
        assertThatThrownBy(() -> paymentFacade.payment(token, reservation.id(), 2L)) // 예약자 ID: 1L, 결제자 ID: 2L
                .isInstanceOf(CoreException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.PAYMENT_DIFFERENT_USER);
    }

    @Test
    void 예약한지_5분이_지났을_경우_PAYMENT_TIMEOUT_에러를_반환한다() {
        Reservation timeHasPassedReservation =
                new Reservation(2L, 1L, 1L, 1L, USER_ID,
                        ReservationStatus.PAYMENT_WAITING, LocalDateTime.now().minusMinutes(6)); // 5분 전 예약건으로 생성
        reservationRepository.save(timeHasPassedReservation);
        // when & then
        assertThatThrownBy(() -> paymentFacade.payment(token, timeHasPassedReservation.id(), USER_ID))
                .isInstanceOf(CoreException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.PAYMENT_TIMEOUT);
    }

    @Test
    void 이미_결제된_예약건이라면_ALREADY_PAID_에러를_반환한다() {
        Reservation alreadyReserved =
                new Reservation(2L, 1L, 1L, 1L, USER_ID,
                        ReservationStatus.COMPLETED, LocalDateTime.now().minusMinutes(6));
        reservationRepository.save(alreadyReserved);
        // when & then
        assertThatThrownBy(() -> paymentFacade.payment(token, alreadyReserved.id(), USER_ID))
                .isInstanceOf(CoreException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.ALREADY_PAID);
    }
}

package hhplus.concert.application.facade;

import hhplus.concert.domain.model.*;
import hhplus.concert.domain.repository.*;
import hhplus.concert.domain.service.*;
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
    private BalanceService balanceService;

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
    private BalanceRepository balanceRepository;

    @Autowired
    private ConcertRepository concertRepository;

    private Concert concert;
    private ConcertSchedule schedule;
    private Reservation reservation;
    private Seat seat;

    @BeforeEach
    void setUp() {
        concert = new Concert(1L, "Test Concert", "Test Description", ConcertStatus.AVAILABLE);
        schedule = new ConcertSchedule(1L, 1L, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(5), LocalDateTime.now().plusDays(2));
        seat = new Seat(1L, schedule.id(), 1, SeatStatus.AVAILABLE, null, 10000);
        reservation = new Reservation(1L, 1L, 1L, 1L, 1L, ReservationStatus.PAYMENT_WAITING, LocalDateTime.now());

        concertRepository.saveConcert(concert);
        concertRepository.saveSchedule(schedule);
        concertRepository.saveSeat(seat);
        reservationRepository.save(reservation);
    }

    @Test
    void 결제_성공_통합테스트() {
        // given
        Long reservationId = 1L;
        Long userId = 1L;
        queueService.createToken(userId);
        Queue token = queueService.getToken(userId);

        Balance balance = balanceService.getBalance(userId);
        balanceService.chargeBalance(userId, 10000L);

        // when
        Payment payment = paymentFacade.payment(token.token(), reservationId, userId);

        // then
        assertThat(payment).isNotNull();
        assertThat(payment.userId()).isEqualTo(userId);
        assertThat(payment.reservationId()).isEqualTo(reservationId);

        Reservation updatedReservation = reservationRepository.findById(reservationId);
        assertThat(updatedReservation.status()).isEqualTo("COMPLETED");

        Balance userBalance = balanceService.getBalance(userId);
        Seat reservedSeat = concertService.getSeat(updatedReservation.seatId());
        assertThat(userBalance.amount()).isLessThanOrEqualTo(0); // 잔액 차감 확인
    }

    @Test
    void 결제_실패_잔액부족_통합테스트() {
        // given
        Long reservationId = 1L;  // 실제 예약 ID
        Long userId = 1L;  // 실제 유저 ID
        queueService.createToken(userId);
        Queue token = queueService.getToken(userId);

        // 잔액이 부족하도록 설정
        Balance balance = balanceService.getBalance(userId);
        balanceService.chargeBalance(userId, 0L); // 잔액 0으로 설정

        // when & then
        assertThatThrownBy(() -> paymentFacade.payment(token.token(), reservationId, userId))
                .isInstanceOf(CustomException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.PAYMENT_FAILED_AMOUNT);
    }
}

package hhplus.concert.application.facade;

import hhplus.concert.domain.model.Point;
import hhplus.concert.domain.model.Payment;
import hhplus.concert.domain.model.Reservation;
import hhplus.concert.domain.model.Seat;
import hhplus.concert.domain.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentFacade {

    private final QueueService queueService;
    private final ReservationService reservationService;
    private final PaymentService paymentService;
    private final PointService pointService;
    private final ConcertService concertService;

    public Payment payment(String token, Long reservationId, Long userId) {
        // 토큰 유효성 검증
        queueService.validateToken(token);
        // 예약 검증 (본인인지, 시간 오버 안됐는지)
        Reservation reservation = reservationService.checkReservation(reservationId, userId);
        Seat seat = concertService.getSeat(reservation.seatId());
        Point point = pointService.getPoint(userId);
        // 잔액을 확인한다.
        pointService.usePoint(point, seat.seatPrice());

        // 예약 상태를 변경한다.
        Reservation reserved = reservationService.changeStatus(reservation);
        // 결제 내역을 생성한다.
        return paymentService.createBill(reserved.id(), userId, seat.seatPrice());
    }
}

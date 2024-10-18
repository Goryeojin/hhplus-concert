package hhplus.concert.domain.model;

import hhplus.concert.support.exception.CustomException;
import hhplus.concert.support.exception.ErrorCode;
import hhplus.concert.support.type.ReservationStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class ReservationTest {

    @Test
    void 예약_검증_성공() {
        // given
        Reservation reservation = Reservation.builder()
                .id(1L)
                .concertId(1L)
                .scheduleId(1L)
                .seatId(1L)
                .userId(1L)
                .status(ReservationStatus.PAYMENT_WAITING)
                .reservationAt(LocalDateTime.now())  // 현재 시간으로 예약
                .build();

        // when / then
        reservation.checkValidation(1L); // 동일한 유저로 검증
    }

    @Test
    void 예약_검증_실패_결제시간초과() {
        // given
        Reservation reservation = Reservation.builder()
                .id(1L)
                .concertId(1L)
                .scheduleId(1L)
                .seatId(1L)
                .userId(1L)
                .status(ReservationStatus.PAYMENT_WAITING)
                .reservationAt(LocalDateTime.now().minusMinutes(6))  // 6분 전에 예약
                .build();

        // when / then
        assertThatThrownBy(() -> reservation.checkValidation(1L))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.PAYMENT_TIMEOUT.getMessage());
    }

    @Test
    void 예약_검증_실패_다른유저() {
        // given
        Reservation reservation = Reservation.builder()
                .id(1L)
                .concertId(1L)
                .scheduleId(1L)
                .seatId(1L)
                .userId(1L)
                .status(ReservationStatus.PAYMENT_WAITING)
                .reservationAt(LocalDateTime.now())  // 현재 시간으로 예약
                .build();

        // when / then
        assertThatThrownBy(() -> reservation.checkValidation(2L))  // 다른 유저 ID
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.PAYMENT_DIFFERENT_USER.getMessage());
    }

    @Test
    void 예약_상태_변경_성공() {
        // given
        Reservation reservation = Reservation.builder()
                .id(1L)
                .concertId(1L)
                .scheduleId(1L)
                .seatId(1L)
                .userId(1L)
                .status(ReservationStatus.PAYMENT_WAITING)
                .reservationAt(LocalDateTime.now())
                .build();

        // when
        Reservation updatedReservation = reservation.changeStatus();

        // then
        assertThat(updatedReservation.status()).isEqualTo(ReservationStatus.COMPLETED);
        assertThat(updatedReservation.id()).isEqualTo(reservation.id());
        assertThat(updatedReservation.concertId()).isEqualTo(reservation.concertId());
    }

}
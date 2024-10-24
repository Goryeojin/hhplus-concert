package hhplus.concert.infra.repository.impl;

import hhplus.concert.domain.model.Reservation;
import hhplus.concert.domain.repository.ReservationRepository;
import hhplus.concert.infra.entity.ReservationEntity;
import hhplus.concert.infra.repository.jpa.ReservationJpaRepository;
import hhplus.concert.support.exception.CoreException;
import hhplus.concert.support.code.ErrorCode;
import hhplus.concert.support.type.ReservationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepository {

    private final ReservationJpaRepository reservationJpaRepository;

    @Override
    public Reservation save(Reservation reservation) {
        ReservationEntity entity = reservationJpaRepository.save(ReservationEntity.from(reservation));
        return entity.of(entity);
    }

    @Override
    public Reservation findById(Long reservationId) {
        return reservationJpaRepository.findByReservationId(reservationId)
                .map(entity -> entity.of(entity))
                .orElseThrow(() -> new CoreException(ErrorCode.RESERVATION_NOT_FOUND));
    }

    @Override
    public List<Reservation> findByConcertIdAndScheduleIdAndSeatId(Long concertId, Long scheduleId, Long seatId) {
        return reservationJpaRepository.findByConcertIdAndScheduleIdAndSeatId(concertId, scheduleId, seatId).stream()
                .map(entity -> entity.of(entity))
                .toList();
    }

    @Override
    public List<Reservation> findExpiredReservation(ReservationStatus reservationStatus, LocalDateTime localDateTime) {
        return reservationJpaRepository.findByStatusAndReservationAtBefore(reservationStatus, localDateTime).stream()
                .map(entity -> entity.of(entity))
                .toList();
    }
}

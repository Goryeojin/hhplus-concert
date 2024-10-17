package hhplus.concert.infra.repository.impl;

import hhplus.concert.domain.model.Reservation;
import hhplus.concert.domain.repository.ReservationRepository;
import hhplus.concert.infra.entity.ReservationEntity;
import hhplus.concert.infra.repository.jpa.ReservationJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepository {

    private final ReservationJpaRepository reservationJpaRepository;

    @Override
    public Reservation save(Reservation reservation) {
        return ReservationEntity.of(reservationJpaRepository.save(ReservationEntity.from(reservation)));
    }
}

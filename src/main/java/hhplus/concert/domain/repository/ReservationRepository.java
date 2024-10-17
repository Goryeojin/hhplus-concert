package hhplus.concert.domain.repository;

import hhplus.concert.domain.model.Reservation;

public interface ReservationRepository {
    Reservation save(Reservation reservation);

    Reservation findById(Long aLong);
}

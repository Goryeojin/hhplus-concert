package hhplus.concert.infra.repository.jpa;

import hhplus.concert.infra.entity.ReservationEntity;
import hhplus.concert.support.type.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationJpaRepository extends JpaRepository<ReservationEntity, Long> {
    List<ReservationEntity> findByStatusAndReservationAtBefore(ReservationStatus reservationStatus, LocalDateTime localDateTime);
}

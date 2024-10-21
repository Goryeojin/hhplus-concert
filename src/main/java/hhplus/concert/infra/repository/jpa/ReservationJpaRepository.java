package hhplus.concert.infra.repository.jpa;

import hhplus.concert.infra.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationJpaRepository extends JpaRepository<ReservationEntity, Long> {
}

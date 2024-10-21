package hhplus.concert.infra.repository.jpa;

import hhplus.concert.infra.entity.ConcertEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertJpaRepository extends JpaRepository<ConcertEntity, Long> {
}

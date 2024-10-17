package hhplus.concert.infra.repository.jpa;

import hhplus.concert.infra.entity.ConcertEntity;
import hhplus.concert.support.type.ConcertStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConcertJpaRepository extends JpaRepository<ConcertEntity, Long> {
}

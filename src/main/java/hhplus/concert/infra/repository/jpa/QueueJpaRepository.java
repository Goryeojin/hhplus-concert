package hhplus.concert.infra.repository.jpa;

import hhplus.concert.infra.entity.QueueEntity;
import hhplus.concert.support.type.QueueStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface QueueJpaRepository extends JpaRepository<QueueEntity, Long> {
    Optional<QueueEntity> findByUserId(Long userId);
    Long countByStatusIn(QueueStatus... statuses);
    Optional<QueueEntity> findByToken(String token);
    Long countByIdLessThanAndStatusIn(Long id, List<QueueStatus> statuses);
}

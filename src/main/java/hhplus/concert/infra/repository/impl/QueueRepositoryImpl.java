package hhplus.concert.infra.repository.impl;

import hhplus.concert.domain.model.Queue;
import hhplus.concert.domain.repository.QueueRepository;
import hhplus.concert.infra.entity.QueueEntity;
import hhplus.concert.infra.repository.jpa.QueueJpaRepository;
import hhplus.concert.support.exception.CustomException;
import hhplus.concert.support.exception.ErrorCode;
import hhplus.concert.support.type.QueueStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class QueueRepositoryImpl implements QueueRepository {

    private final QueueJpaRepository queueJpaRepository;

    @Override
    public Queue findQueue(Long userId) {
        return queueJpaRepository.findByUserId(userId)
                .map(QueueEntity::toDomain)
                .orElse(null);
    }

    @Override
    public Queue findQueue(String token) {
        return queueJpaRepository.findByToken(token)
                .map(QueueEntity::toDomain)
                .orElseThrow(() -> new CustomException(ErrorCode.TOKEN_NOT_FOUND));
    }

    @Override
    public Long findActiveCount() {
        return queueJpaRepository.countByStatusIn(QueueStatus.ACTIVE);
    }

    @Override
    public Long findRemainingQueue(Long queueId) {
        return queueJpaRepository.countByIdLessThanAndStatusIn(queueId, List.of(QueueStatus.ACTIVE, QueueStatus.WAITING));
    }

    @Override
    public void save(Queue token) {
        queueJpaRepository.save(QueueEntity.from(token));
    }
}

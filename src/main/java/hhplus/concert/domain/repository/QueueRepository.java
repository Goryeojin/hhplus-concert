package hhplus.concert.domain.repository;

import hhplus.concert.domain.model.Queue;

public interface QueueRepository {

    Queue findQueue(Long userId);
    Queue findQueue(String token);
    Long findActiveCount();
    Long findCurrentRank();
    Long findUserRank(Long queueId);
    Queue save(Queue token);
    void expireToken(Queue expiredToken);
}

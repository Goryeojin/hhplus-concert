package hhplus.concert.domain.repository;

import hhplus.concert.domain.model.Queue;

public interface QueueRepository {

    Queue findQueue(Long userId);
    Queue findQueue(String token);
    Long findActiveCount();
    Long findRemainingQueue(Long queueId);
    void save(Queue token);
}

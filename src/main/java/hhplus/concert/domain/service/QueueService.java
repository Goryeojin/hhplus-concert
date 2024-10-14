package hhplus.concert.domain.service;

import hhplus.concert.domain.model.Queue;
import hhplus.concert.domain.repository.QueueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueueService {

    private final QueueRepository queueRepository;
    public Queue findToken(Long userId) {
        return queueRepository.findQueue(userId);
    }

    public Queue findToken(String token) {
        return queueRepository.findQueue(token);
    }

    public Queue createToken(Long userId) {
        Long activeCount = queueRepository.findActiveCount();
        Queue token = Queue.createToken(userId, activeCount);
        queueRepository.save(token);
        return token;
    }

    public Queue findRemainingQueue(Queue queue) {
        // 나보다 먼저 대기열에 등록되어 있으면서, ACTIVE 또는 WAITING 상태인 대기열 조회
        Long remainingQueue = queueRepository.findRemainingQueue(queue.id());
        return Queue.builder()
                .createdAt(queue.createdAt())
                .status(queue.status())
                .remainingQueueCount(remainingQueue)
                .build();
    }
}

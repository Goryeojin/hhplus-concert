package hhplus.concert.domain.model;

import hhplus.concert.support.type.QueueStatus;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record Queue (
        Long id,
        Long userId,
        String token,
        QueueStatus status,
        LocalDateTime createdAt,
        LocalDateTime enteredAt,
        LocalDateTime expiredAt,
        Long remainingQueueCount
) {
    public static Queue createToken(Long userId, Long activeCount) {
        String userData = userId + "";
        String token = UUID.nameUUIDFromBytes(userData.getBytes()).toString();

        return Queue.builder()
                .userId(userId)
                .token(token)
                .status((activeCount < 50) ? QueueStatus.ACTIVE : QueueStatus.WAITING)
                .createdAt(LocalDateTime.now())
                .enteredAt((activeCount < 50) ? LocalDateTime.now() : null)
                .expiredAt((activeCount < 50) ? LocalDateTime.now().plusMinutes(5) : null)
                .build();
    }
}

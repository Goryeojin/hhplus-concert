package hhplus.concert.domain.model;

import hhplus.concert.support.exception.CustomException;
import hhplus.concert.support.exception.ErrorCode;
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
        Long rank
) {
    public static Queue createToken(Long userId, Long activeCount, Long rank) {
        QueueStatus status = (rank == 0 && activeCount < 50) ? QueueStatus.ACTIVE : QueueStatus.WAITING;
        LocalDateTime now = LocalDateTime.now();

        String userData = userId + now.toString();
        String token = UUID.nameUUIDFromBytes(userData.getBytes()).toString();

        return Queue.builder()
                .userId(userId)
                .token(token)
                .rank((status.equals(QueueStatus.WAITING)) ? ++rank : 0)
                .status(status)
                .createdAt(now)
                .enteredAt((status.equals(QueueStatus.ACTIVE) ? now : null))
                .expiredAt((status.equals(QueueStatus.ACTIVE) ? now.plusMinutes(10) : null))
                .build();
    }

    public Queue expired() {
        return Queue.builder()
                .id(this.id)
                .userId(this.userId)
                .token(this.token)
                .createdAt(this.createdAt)
                .enteredAt(this.enteredAt)
                .status(QueueStatus.EXPIRED)
                .expiredAt(LocalDateTime.now())
                .build();
    }

    public boolean checkStatus() {
        if (status.equals(QueueStatus.EXPIRED)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }
        return status.equals(QueueStatus.ACTIVE);
    }

    public void validateToken() {
        // 토큰이 대기 상태이거나 만료되었을 경우
        if (expiredAt == null || expiredAt.isBefore(LocalDateTime.now())) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }
    }
}
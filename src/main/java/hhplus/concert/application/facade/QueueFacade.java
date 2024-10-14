package hhplus.concert.application.facade;

import hhplus.concert.domain.model.Queue;
import hhplus.concert.domain.service.QueueService;
import hhplus.concert.domain.service.UserService;
import hhplus.concert.support.type.QueueStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QueueFacade {

    private final UserService userService;
    private final QueueService queueService;

    @Transactional
    public Queue createToken(Long userId) {
        // 유저 유무 확인
        userService.existsUser(userId);

        // 토큰 유무 확인
        Queue token = queueService.findToken(userId);

        // 토큰이 존재하고, 대기 상태나 활성 상태라면 그대로 반환
        if (token != null && !token.status().equals(QueueStatus.EXPIRED)) {
            return token;
        }

        // 토큰이 없거나 만료되었을 경우 새로 생성한다.
        return queueService.createToken(userId);
    }

    public Queue getToken(Long userId) {
        userService.existsUser(userId);
        return queueService.findToken(userId);
    }

    public Queue getStatus(String token, Long userId) {
        // 유저 유무 확인
        userService.existsUser(userId);

        // 토큰 유무 확인
        Queue queue = queueService.findToken(token);

        // 내 대기열이 활성화 되었거나 만료 되었다면 바로 반환
        if (queue.status().equals(QueueStatus.ACTIVE) || queue.status().equals(QueueStatus.EXPIRED)) {
            return queue;
        }
        // 남은 대기자 수 조회
        return queueService.findRemainingQueue(queue);
    }
}

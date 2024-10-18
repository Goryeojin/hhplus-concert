package hhplus.concert.application.facade;

import hhplus.concert.domain.model.Queue;
import hhplus.concert.domain.service.QueueService;
import hhplus.concert.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class QueueFacade {

    private final UserService userService;
    private final QueueService queueService;

    // 토큰 생성
    @Transactional
    public Queue createToken(Long userId) {
        // 유저 유무 확인
        userService.existsUser(userId);
        // 토큰 유무 확인
        Queue token = queueService.getToken(userId);
        // 기존에 있던 토큰 만료 처리
        if (token != null) queueService.expireToken(token);
        // 토큰이 없거나 만료되었을 경우 새로 생성한다.
        return queueService.createToken(userId);
    }

    // 대기열 상태 조회
    public Queue getStatus(String token, Long userId) {
        // 유저 유무 확인
        userService.existsUser(userId);
        // 토큰 유무 확인
        Queue queue = queueService.getToken(token);
        // 대기열 상태 확인
        return queueService.checkQueueStatus(queue);
    }
}

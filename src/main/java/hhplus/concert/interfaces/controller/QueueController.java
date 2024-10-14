package hhplus.concert.interfaces.controller;

import hhplus.concert.application.facade.QueueFacade;
import hhplus.concert.domain.model.Queue;
import hhplus.concert.interfaces.dto.QueueDto;
import hhplus.concert.interfaces.dto.TokenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/queue")
@RequiredArgsConstructor
public class QueueController {

    private final QueueFacade queueFacade;

    /**
     * Queue(대기열)에 등록하고, Token 을 발급한다.
     * @param request userId(사용자 ID)
     * @return token
     */
    @PostMapping("/tokens")
    public ResponseEntity<TokenDto.Response> createToken(@RequestBody TokenDto.Request request) {
        Queue token = queueFacade.createToken(request.userId());
        log.info("created token: {}", token);
        return ResponseEntity.ok(
                TokenDto.Response.builder()
                        .token(token.token())
                        .createdAt(token.createdAt())
                        .build()
        );
    }

    /**
     * Token 을 조회한다.
     * @param userId 사용자 ID
     * @return token
     */
    @GetMapping("/tokens")
    public ResponseEntity<TokenDto.Response> getToken(@RequestHeader("User-Id") Long userId) {
        Queue token = queueFacade.getToken(userId);
        if (token == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(
                TokenDto.Response.builder()
                        .token(token.token())
                        .createdAt(token.createdAt())
                        .build()
        );
    }

    /**
     * Queue(대기열) 상태를 조회한다.
     * @param token 발급받은 토큰
     * @param userId 사용자 ID
     * @return 대기열 상태 dto
     */
    @GetMapping("/status")
    public ResponseEntity<QueueDto.Response> getStatus(
            @RequestHeader("Token") String token,
            @RequestHeader("User-Id") Long userId
    ) {
        Queue queue = queueFacade.getStatus(token, userId);
        return ResponseEntity.ok(
                QueueDto.Response.builder()
                        .createdAt(queue.createdAt())
                        .status(queue.status())
                        .remainingQueueCount(queue.remainingQueueCount())
                        .enteredAt(queue.enteredAt())
                        .expiredAt(queue.expiredAt())
                        .build()
        );
    }
}

package hhplus.concert.interfaces.controller;

import hhplus.concert.interfaces.dto.QueueDto;
import hhplus.concert.interfaces.dto.TokenDto;
import hhplus.concert.support.type.QueueStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/queue")
public class QueueController {

    /**
     * Queue(대기열)에 등록하고, Token 을 발급한다.
     * @param request userId(사용자 ID)
     * @return token
     */
    @PostMapping("/queue/tokens")
    public ResponseEntity<TokenDto.Response> createToken(@RequestBody TokenDto.Request request) {
        return ResponseEntity.ok(
                TokenDto.Response.builder()
                        .token("a1b2c3d4")
                        .createdAt(LocalDateTime.now())
                        .expiredAt(LocalDateTime.now().plusMinutes(10L)).build()
        );
    }

    /**
     * Token 을 조회한다.
     * @param userId 사용자 ID
     * @return token
     */
    @GetMapping("/queue/tokens")
    public ResponseEntity<TokenDto.Response> getToken(@RequestHeader("User-Id") String userId) {
        return ResponseEntity.ok(
                TokenDto.Response.builder()
                        .token("a1b2c3d4")
                        .createdAt(LocalDateTime.now())
                        .expiredAt(LocalDateTime.now().plusMinutes(10L)).build()
        );
    }

    /**
     * Queue(대기열) 상태를 조회한다.
     * @param token 발급받은 토큰
     * @param userId 사용자 ID
     * @return 대기열 상태 dto
     */
    @GetMapping("/queue/status")
    public ResponseEntity<QueueDto.Response> getStatus(
            @RequestHeader("Token") String token,
            @RequestHeader("User-Id") Long userId
    ) {
        return ResponseEntity.ok(
                QueueDto.Response.builder()
                        .createdAt(LocalDateTime.now())
                        .status(QueueStatus.WAITING)
                        .remainingQueueCount(10L).build()
        );
    }
}

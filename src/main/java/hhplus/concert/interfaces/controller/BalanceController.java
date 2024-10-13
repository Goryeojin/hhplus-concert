package hhplus.concert.interfaces.controller;

import hhplus.concert.interfaces.dto.BalanceDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/balance")
public class BalanceController {

    /**
     * 잔액을 조회한다.
     * @param userId 사용자 ID
     * @return 잔액 dto
     */
    @GetMapping("/users/{userId}/balance")
    public ResponseEntity<BalanceDto.Response> getBalance(@PathVariable Long userId) {
        return ResponseEntity.ok(
                BalanceDto.Response.builder()
                        .userId(1L)
                        .currentAmount(50000L).build()
        );
    }

    /**
     * 잔액을 충전한다.
     * @param userId 사용자 ID
     * @param request 충전할 금액
     * @return 잔액 dto
     */
    @PatchMapping("/users/{userId}/balance")
    public ResponseEntity<BalanceDto.Response> chargeBalance(
            @PathVariable Long userId,
            @RequestBody BalanceDto.Request request
    ) {
        return ResponseEntity.ok(
                BalanceDto.Response.builder()
                        .userId(1L)
                        .currentAmount(50000L).build()
        );
    }
}

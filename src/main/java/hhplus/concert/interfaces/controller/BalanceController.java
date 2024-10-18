package hhplus.concert.interfaces.controller;

import hhplus.concert.application.facade.BalanceFacade;
import hhplus.concert.domain.model.Balance;
import hhplus.concert.domain.service.BalanceService;
import hhplus.concert.interfaces.dto.BalanceDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class BalanceController {

    private final BalanceFacade balanceFacade;

    /**
     * 잔액을 조회한다.
     * @param userId 사용자 ID
     * @return 잔액 dto
     */
    @GetMapping("/users/{userId}/balance")
    public ResponseEntity<BalanceDto.BalanceResponse> getBalance(@PathVariable Long userId) {
        Balance balance = balanceFacade.getBalance(userId);
        return ResponseEntity.ok(
                BalanceDto.BalanceResponse.builder()
                        .userId(balance.userId())
                        .currentAmount(balance.amount()).build()
        );
    }

    /**
     * 잔액을 충전한다.
     * @param userId 사용자 ID
     * @param request 충전할 금액
     * @return 잔액 dto
     */
    @PatchMapping("/users/{userId}/balance")
    public ResponseEntity<BalanceDto.BalanceResponse> chargeBalance(
            @PathVariable Long userId,
            @Valid @RequestBody BalanceDto.BalanceRequest request
    ) {
        Balance balance = balanceFacade.chargeBalance(userId, request.amount());
        return ResponseEntity.ok(
                BalanceDto.BalanceResponse.builder()
                        .userId(balance.userId())
                        .currentAmount(balance.amount()).build()
        );
    }
}

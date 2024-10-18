package hhplus.concert.application.facade;

import hhplus.concert.domain.model.Balance;
import hhplus.concert.domain.service.BalanceService;
import hhplus.concert.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BalanceFacade {

    private final UserService userService;
    private final BalanceService balanceService;

    public Balance getBalance(Long userId) {
        userService.existsUser(userId);
        return balanceService.getBalance(userId);
    }

    public Balance chargeBalance(Long userId, Long amount) {
        userService.existsUser(userId);
        return balanceService.chargeBalance(userId, amount);
    }
}

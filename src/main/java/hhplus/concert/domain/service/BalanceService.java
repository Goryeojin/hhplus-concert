package hhplus.concert.domain.service;

import hhplus.concert.domain.model.Balance;
import hhplus.concert.domain.repository.BalanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BalanceService {

    private final BalanceRepository balanceRepository;

    public Balance getBalance(Long userId) {
        return balanceRepository.findBalance(userId);
    }

    public Balance chargeBalance(Long userId, Long amount) {
        Balance balance = balanceRepository.findBalance(userId);
        Balance updatedBalance = balance.charge(amount);
        balanceRepository.save(updatedBalance);
        return updatedBalance;
    }

    public void useBalance(Balance balance, int amount) {
        Balance usedBalance = balance.useBalance(amount);
        balanceRepository.save(usedBalance);
    }
}

package hhplus.concert.domain.repository;

import hhplus.concert.domain.model.Balance;

public interface BalanceRepository {
    Balance findBalance(Long userId);

    void save(Balance updatedBalance);
}

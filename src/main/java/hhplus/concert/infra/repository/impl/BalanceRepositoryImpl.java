package hhplus.concert.infra.repository.impl;

import hhplus.concert.domain.model.Balance;
import hhplus.concert.domain.repository.BalanceRepository;
import hhplus.concert.infra.entity.BalanceEntity;
import hhplus.concert.infra.repository.jpa.BalanceJpaRepository;
import hhplus.concert.support.exception.CustomException;
import hhplus.concert.support.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BalanceRepositoryImpl implements BalanceRepository {

    private final BalanceJpaRepository balanceJpaRepository;

    @Override
    public Balance findBalance(Long userId) {
        return balanceJpaRepository.findByUserId(userId)
                .map(BalanceEntity::of)
                .orElseThrow(() -> new CustomException(ErrorCode.INTERNAL_SERVER_ERROR));
    }

    @Override
    public void save(Balance updatedBalance) {
        BalanceEntity entity = BalanceEntity.from(updatedBalance);
        balanceJpaRepository.save(entity);
    }
}

package hhplus.concert.application.facade;

import hhplus.concert.domain.model.Balance;
import hhplus.concert.domain.service.BalanceService;
import hhplus.concert.infra.repository.jpa.BalanceJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class BalanceFacadeIntegrationTest {

    @Autowired
    private BalanceFacade balanceFacade;

    @Autowired
    private BalanceService balanceService;

    @Autowired
    private BalanceJpaRepository balanceJpaRepository;

    @Test
    void 잔액충전() {
        // given
        Long userId = 1L;
        Long chargeAmount = 500L;

        // when
        Balance updatedBalance = balanceFacade.chargeBalance(userId, chargeAmount);

        // then
        assertThat(updatedBalance.amount()).isEqualTo(500L);
        assertThat(updatedBalance.userId()).isEqualTo(userId);

        Balance fetchedBalance = balanceService.getBalance(userId);
        assertThat(fetchedBalance.amount()).isEqualTo(500L);
    }

    @Test
    void 잔액조회() {
        // given
        Long userId = 1L;

        // when
        Balance fetchedBalance = balanceFacade.getBalance(userId);

        // then
        assertThat(fetchedBalance.amount()).isEqualTo(0L);
        assertThat(fetchedBalance.userId()).isEqualTo(userId);
    }
}

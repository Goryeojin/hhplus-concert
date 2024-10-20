package hhplus.concert.application.facade;

import hhplus.concert.domain.model.Point;
import hhplus.concert.domain.service.PointService;
import hhplus.concert.infra.repository.jpa.PointJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class PointFacadeIntegrationTest {

    @Autowired
    private PointFacade pointFacade;

    @Autowired
    private PointService balanceService;

    @Autowired
    private PointJpaRepository balanceJpaRepository;

    @Test
    void 잔액충전() {
        // given
        Long userId = 1L;
        Long chargeAmount = 500L;

        // when
        Point updatedPoint = pointFacade.chargePoint(userId, chargeAmount);

        // then
        assertThat(updatedPoint.amount()).isEqualTo(500L);
        assertThat(updatedPoint.userId()).isEqualTo(userId);

        Point fetchedPoint = balanceService.getPoint(userId);
        assertThat(fetchedPoint.amount()).isEqualTo(500L);
    }

    @Test
    void 잔액조회() {
        // given
        Long userId = 1L;

        // when
        Point fetchedPoint = pointFacade.getPoint(userId);

        // then
        assertThat(fetchedPoint.amount()).isEqualTo(0L);
        assertThat(fetchedPoint.userId()).isEqualTo(userId);
    }
}

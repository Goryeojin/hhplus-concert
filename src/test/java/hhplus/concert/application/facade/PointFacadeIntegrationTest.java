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

    private final Long USER_ID = 1L;
    @Test
    void 유저의_잔액을_충전한다() {
        // given
        Long chargeAmount = 500L;

        // when
        Point updatedPoint = pointFacade.chargePoint(USER_ID, chargeAmount);

        // then
        assertThat(updatedPoint.amount()).isEqualTo(500L);
        assertThat(updatedPoint.userId()).isEqualTo(USER_ID);

        Point fetchedPoint = balanceService.getPoint(USER_ID);
        assertThat(fetchedPoint.amount()).isEqualTo(500L);
    }

    @Test
    void 유저의_잔액을_조회한다() {
        // when
        Point fetchedPoint = pointFacade.getPoint(USER_ID);

        // then
        assertThat(fetchedPoint.amount()).isEqualTo(0L); // 초기 유저의 잔액은 0이다
        assertThat(fetchedPoint.userId()).isEqualTo(USER_ID);
    }
}

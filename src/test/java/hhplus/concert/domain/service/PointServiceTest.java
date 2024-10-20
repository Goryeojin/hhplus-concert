package hhplus.concert.domain.service;

import hhplus.concert.domain.model.Point;
import hhplus.concert.domain.repository.PointRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class PointServiceTest {

    @Mock
    private PointRepository balanceRepository;

    @InjectMocks
    private PointService balanceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void 유저의_잔액을_조회한() {
        // given
        Long userId = 1L;
        Point balance = Point.builder()
                .id(1L)
                .userId(userId)
                .amount(1000L)
                .lastUpdatedAt(LocalDateTime.now())
                .build();

        when(balanceRepository.findPoint(userId)).thenReturn(balance);

        // when
        Point result = balanceService.getPoint(userId);

        // then
        assertThat(result).isEqualTo(balance);
        verify(balanceRepository, times(1)).findPoint(userId);
    }

    @Test
    void 유저의_잔액을_충전한다() {
        // given
        Long userId = 1L;
        Point balance = Point.builder()
                .id(1L)
                .userId(userId)
                .amount(1000L)
                .lastUpdatedAt(LocalDateTime.now())
                .build();
        Long chargeAmount = 500L;
        Point updatedBalance = balance.charge(chargeAmount);

        when(balanceRepository.findPoint(userId)).thenReturn(balance);
        balanceRepository.save(balance);

        // when
        Point result = balanceService.chargePoint(userId, chargeAmount);

        // then
        assertThat(result).usingRecursiveComparison()
                .ignoringFields("lastUpdatedAt") // lastUpdatedAt 필드를 무시하고 비교
                .isEqualTo(updatedBalance);
        verify(balanceRepository, times(1)).findPoint(userId);
        verify(balanceRepository, times(1)).save(result);
    }

}
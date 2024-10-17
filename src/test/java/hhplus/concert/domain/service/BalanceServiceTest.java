package hhplus.concert.domain.service;

import hhplus.concert.domain.model.Balance;
import hhplus.concert.domain.repository.BalanceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class BalanceServiceTest {

    @Mock
    private BalanceRepository balanceRepository;

    @InjectMocks
    private BalanceService balanceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void 잔액조회() {
        // given
        Long userId = 1L;
        Balance balance = Balance.builder()
                .id(1L)
                .userId(userId)
                .amount(1000L)
                .lastUpdatedAt(LocalDateTime.now())
                .build();

        when(balanceRepository.findBalance(userId)).thenReturn(balance);

        // when
        Balance result = balanceService.getBalance(userId);

        // then
        assertThat(result).isEqualTo(balance);
        verify(balanceRepository, times(1)).findBalance(userId);
    }

    @Test
    void 잔액충전() {
        // given
        Long userId = 1L;
        Balance balance = Balance.builder()
                .id(1L)
                .userId(userId)
                .amount(1000L)
                .lastUpdatedAt(LocalDateTime.now())
                .build();
        Long chargeAmount = 500L;
        Balance updatedBalance = balance.charge(chargeAmount);

        when(balanceRepository.findBalance(userId)).thenReturn(balance);
        balanceRepository.save(balance);

        // when
        Balance result = balanceService.chargeBalance(userId, chargeAmount);

        // then
        assertThat(result).usingRecursiveComparison()
                .ignoringFields("lastUpdatedAt") // lastUpdatedAt 필드를 무시하고 비교
                .isEqualTo(updatedBalance);
        verify(balanceRepository, times(1)).findBalance(userId);
        verify(balanceRepository, times(1)).save(result);
    }

}
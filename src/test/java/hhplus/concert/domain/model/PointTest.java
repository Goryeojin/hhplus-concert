package hhplus.concert.domain.model;

import hhplus.concert.support.exception.CustomException;
import hhplus.concert.support.exception.ErrorCode;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PointTest {

    @Test
    void 잔액_사용_성공() {
        // given
        Point balance = Point.builder()
                .id(1L)
                .userId(1L)
                .amount(10000L)  // 잔액 10,000원
                .lastUpdatedAt(LocalDateTime.now())
                .build();

        // when
        Point updatedBalance = balance.useBalance(5000);  // 5,000원 사용

        // then
        assertThat(updatedBalance.amount()).isEqualTo(5000L);  // 남은 잔액 5,000원
        assertThat(updatedBalance.lastUpdatedAt()).isAfter(balance.lastUpdatedAt());  // 시간 업데이트 확인
    }

    @Test
    void 잔액_사용_실패_잔액부족() {
        // given
        Point balance = Point.builder()
                .id(1L)
                .userId(1L)
                .amount(3000L)  // 잔액 3,000원
                .lastUpdatedAt(LocalDateTime.now())
                .build();

        // when / then
        assertThatThrownBy(() -> balance.useBalance(5000))  // 5,000원 사용 시도
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.PAYMENT_FAILED_AMOUNT.getMessage());
    }

}
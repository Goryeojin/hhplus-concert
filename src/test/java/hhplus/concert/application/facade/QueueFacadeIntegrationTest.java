package hhplus.concert.application.facade;

import hhplus.concert.domain.model.Queue;
import hhplus.concert.domain.repository.QueueRepository;
import hhplus.concert.domain.service.QueueService;
import hhplus.concert.domain.service.UserService;
import hhplus.concert.infra.repository.jpa.QueueJpaRepository;
import hhplus.concert.support.exception.CoreException;
import hhplus.concert.support.code.ErrorCode;
import hhplus.concert.support.type.QueueStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

@SpringBootTest
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
public class QueueFacadeIntegrationTest {

    @Autowired
    private QueueFacade queueFacade;

    @Autowired
    private QueueService queueService;

    @Autowired
    private UserService userService;

    @Autowired
    private QueueRepository queueRepository;

    @Autowired
    private QueueJpaRepository queueJpaRepository;

    private final Long USER_ID = 1L;

    @Test
    @Transactional
    void 토큰을_생성한다() {
        // given
        Long userId = USER_ID;

        // when
        Queue token = queueFacade.createToken(userId);

        // then
        assertThat(token).isNotNull();
        assertThat(token.userId()).isEqualTo(userId);
        assertThat(token.status()).isEqualTo(QueueStatus.ACTIVE);
    }

    @Test
    void 토큰_생성_시_기존_토큰은_만료_상태로_변경한다() {
        // given
        Long userId = USER_ID;

        // when
        Queue oldToken = queueFacade.createToken(userId);
        queueFacade.createToken(userId); // 같은 사용자로 토큰을 새로 발급 요청한다.

        Queue queue = queueRepository.findQueue(oldToken.token());

        // then
        assertThat(queue).isNotNull();
        assertThat(queue.userId()).isEqualTo(userId);
        assertThat(queue.status()).isEqualTo(QueueStatus.EXPIRED);
    }

    @Test
    @Transactional
    void 만료된_토큰_상태_조회시_예외_발생() {
        // given
        Long userId = USER_ID;
        String uuid = "1234";
        Queue token = Queue.builder()
                .id(USER_ID)
                .token(uuid)
                .userId(userId)
                .status(QueueStatus.EXPIRED)
                .createdAt(LocalDateTime.now())
                .build();

        queueRepository.save(token);

        // when & then
        assertThatThrownBy(() -> queueFacade.getStatus(token.token(), userId))
                .isInstanceOf(CoreException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.UNAUTHORIZED);
    }

    @Test
    void 대기열_상태_조회_성공() {
        // given
        Long userId = USER_ID;
        Queue token = queueFacade.createToken(userId);

        // when
        Queue queueStatus = queueFacade.getStatus(token.token(), userId);

        // then
        assertThat(queueStatus).isNotNull();
        assertThat(queueStatus.token()).isEqualTo(token.token());
        assertThat(queueStatus.status()).isEqualTo(QueueStatus.ACTIVE);
    }
}

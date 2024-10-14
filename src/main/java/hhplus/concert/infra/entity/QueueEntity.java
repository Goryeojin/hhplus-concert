package hhplus.concert.infra.entity;

import hhplus.concert.domain.model.Queue;
import hhplus.concert.support.type.QueueStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "queue")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueueEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private QueueStatus status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime enteredAt;

    private LocalDateTime expiredAt;

    public Queue toDomain() {
        return Queue.builder()
                .id(this.id)
                .userId(this.user.id)
                .token(this.token)
                .status(this.status)
                .createdAt(this.createdAt)
                .enteredAt(this.enteredAt)
                .expiredAt(this.expiredAt)
                .build();
    }

    public static QueueEntity from(Queue queue) {
        return QueueEntity.builder()
                .user(UserEntity.builder().id(queue.userId()).build())
                .token(queue.token())
                .status(queue.status())
                .createdAt(queue.createdAt())
                .enteredAt(queue.enteredAt())
                .expiredAt(queue.expiredAt())
                .build();
    }

}

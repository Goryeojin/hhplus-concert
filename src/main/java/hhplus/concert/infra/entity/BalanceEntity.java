package hhplus.concert.infra.entity;

import hhplus.concert.domain.model.Balance;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "balance")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BalanceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(nullable = false)
    private Long amount;

    private LocalDateTime lastUpdatedAt;

    public static Balance of(BalanceEntity entity) {
        return Balance.builder()
                .id(entity.getId())
                .userId(entity.getUser().getId())
                .amount(entity.getAmount())
                .lastUpdatedAt(entity.getLastUpdatedAt())
                .build();
    }

    public static BalanceEntity from(Balance balance) {
        return BalanceEntity.builder()
                .id(balance.id())
                .user(UserEntity.builder().id(balance.userId()).build())
                .amount(balance.amount())
                .lastUpdatedAt(balance.lastUpdatedAt())
                .build();
    }
}

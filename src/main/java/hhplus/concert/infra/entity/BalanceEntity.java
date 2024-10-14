package hhplus.concert.infra.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name = "balance")
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
}

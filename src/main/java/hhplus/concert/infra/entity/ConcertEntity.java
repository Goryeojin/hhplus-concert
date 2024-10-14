package hhplus.concert.infra.entity;

import hhplus.concert.support.type.ConcertStatus;
import jakarta.persistence.*;

@Entity(name = "concert")
public class ConcertEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ConcertStatus status;
}

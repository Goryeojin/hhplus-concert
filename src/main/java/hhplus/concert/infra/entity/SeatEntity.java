package hhplus.concert.infra.entity;

import jakarta.persistence.*;

@Entity(name = "seat")
public class SeatEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concert_schedule_id", nullable = false)
    private ConcertScheduleEntity concertSchedule;

    @Column(nullable = false)
    private int seatNumber;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private int seatPrice;
}

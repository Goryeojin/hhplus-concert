package hhplus.concert.infra.entity;

import hhplus.concert.domain.model.Seat;
import hhplus.concert.support.type.SeatStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "seat")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeatEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concert_schedule_id", nullable = false)
    private ConcertScheduleEntity concertSchedule;

    @Column(nullable = false)
    private int seatNo;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private SeatStatus status;

    private LocalDateTime reservationAt;

    @Column(nullable = false)
    private int seatPrice;

    public Seat of(SeatEntity entity) {
        return Seat.builder()
                .id(entity.getId())
                .concertScheduleId(entity.getConcertSchedule().getId())
                .seatNo(entity.getSeatNo())
                .status(entity.getStatus())
                .reservationAt(entity.getReservationAt())
                .seatPrice(entity.getSeatPrice())
                .build();
    }

    public static SeatEntity from(Seat seat) {
        return SeatEntity.builder()
                .id(seat.id())
                .concertSchedule(ConcertScheduleEntity.builder().id(seat.concertScheduleId()).build())
                .seatNo(seat.seatNo())
                .status(seat.status())
                .reservationAt(seat.reservationAt())
                .seatPrice(seat.seatPrice())
                .build();
    }
}

package kr.hhplus.be.server.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "tb_seat_reservation", schema = "hhplus", indexes = {
        @Index(name = "idx_user_id", columnList = "user_id")
}, uniqueConstraints = {
        @UniqueConstraint(name = "uniq_concert_seat", columnNames = {"seat_id"})
})
public class TbSeatReservation {
    @Id
    @Column(name = "seat_reservation_id", nullable = false)
    private Long id;

    @Column(name = "seat_id")
    private Long seatId;

    @Column(name = "user_id")
    private Long userId;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "reserved_at")
    private Instant reservedAt;

    @Column(name = "reserved_until")
    private Instant reservedUntil;

}
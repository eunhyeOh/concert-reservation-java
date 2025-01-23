package kr.hhplus.be.server.infrastructure.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "tb_concert_reservation", schema = "hhplus", indexes = {
        @Index(name = "idx_concert_id", columnList = "concert_id"),
        @Index(name = "idx_user_id", columnList = "user_id")
})
public class TbConcertReservation {
    @Id
    @Column(name = "reservation_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "concert_id", nullable = false)
    private Long concertId;

    @NotNull
    @Column(name = "schedule_id", nullable = false)
    private Long scheduleId;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @NotNull
    @Column(name = "payment_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal paymentAmount;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "reserved_at")
    private Instant reservedAt;

}
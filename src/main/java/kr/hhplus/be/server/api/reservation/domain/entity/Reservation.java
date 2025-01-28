package kr.hhplus.be.server.api.reservation.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import kr.hhplus.be.server.api.concert.domain.entity.ConcertSeat;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Entity
@Table(name = "tb_reservation")
public class Reservation {
    @Id
    @Column(name = "reservation_id", nullable = false)
    private Long id;

    @NotNull
    private Long userId;

    @NotNull
    private Long seatId;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "reserved_dt", nullable = false)
    private Instant reservedDt;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "reserved_untill_dt", nullable = false)
    private Instant reservedUntillDt;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_dt", nullable = false)
    private Instant createdDt;

}
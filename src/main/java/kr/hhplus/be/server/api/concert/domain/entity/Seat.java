package kr.hhplus.be.server.api.concert.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "tb_concert_schedule_seat", schema = "hhplus", uniqueConstraints = {
        @UniqueConstraint(name = "uniq_concert_schedule_seat", columnNames = {"concert_id", "schedule_id", "seat_number"})
})
public class Seat {
    @Id
    @Column(name = "seat_id", nullable = false)
    private Long seatId;

    @NotNull
    @Column(name = "concert_id", nullable = false)
    private Long concertId;

    @NotNull
    @Column(name = "schedule_id", nullable = false)
    private Long scheduleId;

    @Size(max = 10)
    @NotNull
    @Column(name = "seat_number", nullable = false, length = 10)
    private String seatNumber;

    @Size(max = 10)
    @NotNull
    @ColumnDefault("'AVAILABLE'")
    @Column(name = "status", nullable = false, length = 10)
    private String status;

    @NotNull
    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

}
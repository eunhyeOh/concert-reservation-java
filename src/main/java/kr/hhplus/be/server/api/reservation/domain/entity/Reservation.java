package kr.hhplus.be.server.api.reservation.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
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
    private LocalDateTime reservedDt;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "reserved_untill_dt", nullable = false)
    private LocalDateTime reservedUntillDt;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_dt", nullable = false)
    private LocalDateTime createdDt;

    public Reservation(Long userId, Long seatId) {
        this.userId = userId;
        this.seatId = seatId;
        this.reservedDt = LocalDateTime.now();
        this.reservedUntillDt = LocalDateTime.now().plusSeconds(300);
        this.createdDt = LocalDateTime.now();
    }

    /**
     * 만료 여부 확인
     * */
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.reservedUntillDt);
    }

    public void expire() {
        this.reservedUntillDt = LocalDateTime.now();
    }

}
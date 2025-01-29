package kr.hhplus.be.server.api.concert.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import kr.hhplus.be.server.common.config.jpa.BaseEntity;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "tb_concert_schedule")
public class ConcertSchedule extends BaseEntity {
    @Id
    @Column(name = "schedule_id", nullable = false)
    private Long id;

    @NotNull
    private Long concertId;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "start_dt", nullable = false)
    private LocalDateTime startDt;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "end_dt", nullable = false)
    private LocalDateTime endDt;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "max_capacity", nullable = false)
    private Integer maxCapacity;


}
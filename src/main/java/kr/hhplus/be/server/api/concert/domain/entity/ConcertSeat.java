package kr.hhplus.be.server.api.concert.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import kr.hhplus.be.server.common.config.jpa.BaseEntity;
import lombok.Getter;

@Getter
@Entity
@Table(name = "tb_concert_seat")
public class ConcertSeat extends BaseEntity {
    @Id
    @Column(name = "seat_id", nullable = false)
    private Long id;

    @NotNull
    private Long scheduleId;

    @NotNull
    @Column(name = "seat_number", nullable = false)
    private Integer seatNumber;

    @NotNull
    @Column(name = "price", nullable = false)
    private Integer price;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ConcertSeatStatus status;

}
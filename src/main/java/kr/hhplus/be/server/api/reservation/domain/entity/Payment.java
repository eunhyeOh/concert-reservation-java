package kr.hhplus.be.server.api.reservation.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import kr.hhplus.be.server.common.config.jpa.BaseEntity;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Entity
@Table(name = "tb_payment")
public class Payment extends BaseEntity {
    @Id
    @Column(name = "payment_id", nullable = false)
    private Long id;

    @NotNull
    private Long userId;

    @NotNull
    private Long reservationId;

    @NotNull
    @Column(name = "price", nullable = false)
    private Integer price;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PaymentStatus status;

}
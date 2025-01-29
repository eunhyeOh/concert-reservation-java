package kr.hhplus.be.server.api.reservation.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import kr.hhplus.be.server.common.config.jpa.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
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

    public Payment(Long userId, Long reservationId, Integer price) {
        this.userId = userId;
        this.reservationId = reservationId;
        this.price = price;
        this.status = PaymentStatus.PROGRESS;
    }

    /**
     * 상태 변경 : 결제 성공
     * */
    public void complete() {
        if(this.getStatus() != PaymentStatus.PROGRESS) {
            throw new IllegalStateException("결제 진행중 상태가 아닙니다.");
        }
        this.status = PaymentStatus.DONE;
    }

    /**
     * 상태 변경 : 결제 실패
     * */
    public void fail() {
        if(this.getStatus() != PaymentStatus.PROGRESS) {
            throw new IllegalStateException("결제 진행중 상태가 아닙니다.");
        }
        this.status = PaymentStatus.FAIL;
    }
}
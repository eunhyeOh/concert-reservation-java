package kr.hhplus.be.server.api.reservation.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import kr.hhplus.be.server.common.config.jpa.BaseEntity;
import lombok.Getter;

@Getter
@Entity
@Table(name = "tb_payment_history")
public class PaymentHistory extends BaseEntity {
    @Id
    @Column(name = "payment_history_id", nullable = false)
    private Long id;

    @NotNull
    private Long userId;

    private Long paymentId;

    @NotNull
    @Column(name = "amount_change", nullable = false)
    private Integer amountChange;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private PaymentHistoryType type;


    protected PaymentHistory() { }

    public PaymentHistory(Long userId, Long paymentId, Integer amountChange, PaymentHistoryType type) {
        this.userId = userId;
        this.paymentId = paymentId;
        this.amountChange = amountChange;
        this.type = type;
    }

}
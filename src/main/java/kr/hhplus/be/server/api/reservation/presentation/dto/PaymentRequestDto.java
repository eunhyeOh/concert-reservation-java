package kr.hhplus.be.server.api.reservation.presentation.dto;

import java.math.BigDecimal;

public class PaymentRequestDto {

    private Long seatId;
    private BigDecimal amount;

    public PaymentRequestDto(Long seatId, BigDecimal amount) {
        this.seatId = seatId;
        this.amount = amount;
    }

    public Long getSeatId() { return seatId; }
    public BigDecimal getAmount() { return amount; }

}

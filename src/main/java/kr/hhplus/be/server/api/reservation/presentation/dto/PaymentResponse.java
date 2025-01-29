package kr.hhplus.be.server.api.reservation.presentation.dto;

import kr.hhplus.be.server.api.reservation.domain.dto.PaymentResult;
import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
public class PaymentResponse {

    private String message;
    private Long paymentId;

    public static PaymentResponse toDto(PaymentResult payment) {
        return PaymentResponse.builder()
                .paymentId(payment.paymentId())
                .message(payment.message())
                .build();
    }
}

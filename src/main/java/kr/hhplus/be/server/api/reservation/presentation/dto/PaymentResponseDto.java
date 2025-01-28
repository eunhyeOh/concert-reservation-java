package kr.hhplus.be.server.api.reservation.presentation.dto;

import kr.hhplus.be.server.api.reservation.domain.entity.Payment;
import kr.hhplus.be.server.api.reservation.domain.entity.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Builder
public class PaymentResponseDto {

    private Long paymentId;
    private Long userId;
    private Long reservationId;
    private Integer price;
    private PaymentStatus status;
    private Instant createAt;

    public static PaymentResponseDto toDto(Payment payment) {
        return PaymentResponseDto.builder()
                .paymentId(payment.getId())
                .userId(payment.getUserId())
                .reservationId(payment.getReservationId())
                .price(payment.getPrice())
                .status(payment.getStatus())
                .build();

    }

    public static List<PaymentResponseDto> toDtoList(List<Payment> payments){

        List<PaymentResponseDto> dtos = new ArrayList<>();
        for (Payment payment : payments) {
            dtos.add(PaymentResponseDto.toDto(payment));
        }
        return dtos;
    }


}

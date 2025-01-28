package kr.hhplus.be.server.api.reservation.presentation.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PaymentRequestDto {

    private Long userId;
    private Long reservationId;
    private Integer price;

}

package kr.hhplus.be.server.api.reservation.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentRequest {

    private Long userId;
    private Long reservationId;
    private Integer price;

}

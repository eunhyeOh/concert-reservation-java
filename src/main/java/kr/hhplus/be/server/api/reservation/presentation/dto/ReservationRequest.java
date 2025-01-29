package kr.hhplus.be.server.api.reservation.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReservationRequest {

    private Long seatId;
    private Long userId;


}

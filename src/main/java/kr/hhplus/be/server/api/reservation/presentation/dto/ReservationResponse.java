package kr.hhplus.be.server.api.reservation.presentation.dto;

import kr.hhplus.be.server.api.reservation.domain.entity.Reservation;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Builder
public class ReservationResponse {
    private Long reservationId;
    private Long seatId;
    private Long userId;
    private LocalDateTime reservedUntillDt;      // 임시 배정 시작 시간
    private LocalDateTime reservedDt;   // 임시 배정 만료 시간
    private LocalDateTime createdDt;


    public static ReservationResponse toDto (Reservation reservation) {
        return ReservationResponse.builder()
                .reservationId(reservation.getId())
                .seatId(reservation.getSeatId())
                .userId(reservation.getUserId())
                .reservedDt(reservation.getReservedDt())
                .reservedUntillDt(reservation.getReservedUntillDt())
                .createdDt(reservation.getCreatedDt())
                .build();
    }

    public static List<ReservationResponse> toDtoList (List<Reservation> reservations) {
        List<ReservationResponse> resultList = new ArrayList<>();
        for (Reservation reservation : reservations) {
            resultList.add(ReservationResponse.toDto(reservation));
        }

        return resultList;
    }

}

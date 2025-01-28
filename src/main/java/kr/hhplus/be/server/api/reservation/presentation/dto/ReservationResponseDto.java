package kr.hhplus.be.server.api.reservation.presentation.dto;

import kr.hhplus.be.server.api.reservation.domain.entity.Reservation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Builder
public class ReservationResponseDto {
    private Long reservationId;
    private Long seatId;
    private Long userId;
    private Instant reservedUntillDt;      // 임시 배정 시작 시간
    private Instant reservedDt;   // 임시 배정 만료 시간
    private Instant createdDt;


    public static ReservationResponseDto toDto (Reservation reservation) {
        return ReservationResponseDto.builder()
                .reservationId(reservation.getId())
                .seatId(reservation.getSeatId())
                .userId(reservation.getUserId())
                .reservedDt(reservation.getReservedDt())
                .reservedUntillDt(reservation.getReservedUntillDt())
                .createdDt(reservation.getCreatedDt())
                .build();
    }

    public static List<ReservationResponseDto> toDtoList (List<Reservation> reservations) {
        List<ReservationResponseDto> dtos = new ArrayList<>();
        for (Reservation reservation : reservations) {
            dtos.add(ReservationResponseDto.toDto(reservation));
        }

        return dtos;
    }

}

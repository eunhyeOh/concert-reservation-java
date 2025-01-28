package kr.hhplus.be.server.api.concert.presentation.dto;

import kr.hhplus.be.server.api.concert.domain.entity.Seat;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Builder
public class SeatResponseDto {

    private Long seatId;
    private Long scheduleId;
    private String seatNumber;
    private String status;
    private BigDecimal price;

    public static SeatResponseDto toDto(Seat seat) {
        return SeatResponseDto.builder()
                .seatId(seat.getSeatId())
                .scheduleId(seat.getScheduleId())
                .seatNumber(seat.getSeatNumber())
                .status(seat.getStatus())
                .price(seat.getPrice())
                .build();

    }

    public static List<SeatResponseDto> toDtoList(List<Seat> seats){

        List<SeatResponseDto> dtos = new ArrayList<>();
        for (Seat seat : seats) {
            dtos.add(SeatResponseDto.toDto(seat));
        }
        return dtos;
    }

}

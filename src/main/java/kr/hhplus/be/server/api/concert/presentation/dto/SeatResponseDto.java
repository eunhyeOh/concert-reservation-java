package kr.hhplus.be.server.api.concert.presentation.dto;

import kr.hhplus.be.server.api.concert.domain.entity.ConcertSeat;
import kr.hhplus.be.server.api.concert.domain.entity.ConcertSeatStatus;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Builder
public class SeatResponseDto {

    private Long seatId;
    private Long scheduleId;
    private Integer seatNumber;
    private ConcertSeatStatus status;
    private Integer price;

    public static SeatResponseDto toDto(ConcertSeat seat) {
        return SeatResponseDto.builder()
                .seatId(seat.getId())
                .scheduleId(seat.getScheduleId())
                .seatNumber(seat.getSeatNumber())
                .status(seat.getStatus())
                .price(seat.getPrice())
                .build();

    }

    public static List<SeatResponseDto> toDtoList(List<ConcertSeat> seats){

        List<SeatResponseDto> dtos = new ArrayList<>();
        for (ConcertSeat seat : seats) {
            dtos.add(SeatResponseDto.toDto(seat));
        }
        return dtos;
    }

}

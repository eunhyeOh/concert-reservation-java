package kr.hhplus.be.server.api.concert.presentation.dto;

import kr.hhplus.be.server.api.concert.domain.dto.SeatResult;
import kr.hhplus.be.server.api.concert.domain.entity.ConcertSeatStatus;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
public class SeatResponse {

    private Long seatId;
    private Long scheduleId;
    private Integer seatNumber;
    private ConcertSeatStatus status;
    private Integer price;
    private LocalDateTime createdAt;

    //record -> dto
    public static SeatResponse toDto(SeatResult seat) {
        return SeatResponse.builder()
                .seatId(seat.seatId())
                .scheduleId(seat.scheduleId())
                .seatNumber(seat.seatNumber())
                .status(seat.status())
                .price(seat.price())
                .createdAt(seat.createdAt())
                .build();

    }

    public static List<SeatResponse> toDtoList(List<SeatResult> seats){

        List<SeatResponse> resultList = new ArrayList<>();
        for (SeatResult seat : seats) {
            resultList.add(SeatResponse.toDto(seat));
        }
        return resultList;
    }

}

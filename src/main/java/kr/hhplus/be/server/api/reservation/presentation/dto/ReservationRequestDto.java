package kr.hhplus.be.server.api.reservation.presentation.dto;

import kr.hhplus.be.server.api.concert.domain.entity.ConcertSchedule;
import kr.hhplus.be.server.api.concert.presentation.dto.ScheduleResponseDto;
import kr.hhplus.be.server.api.reservation.domain.entity.Reservation;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Builder
public class ReservationRequestDto {

    private Long seatId;
    private Long userId;


}

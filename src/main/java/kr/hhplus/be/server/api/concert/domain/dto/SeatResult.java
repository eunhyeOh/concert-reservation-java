package kr.hhplus.be.server.api.concert.domain.dto;

import kr.hhplus.be.server.api.concert.domain.entity.ConcertSeatStatus;
import java.time.LocalDateTime;

public record SeatResult(Long seatId, Long scheduleId, Integer seatNumber, ConcertSeatStatus status, Integer price, LocalDateTime createdAt) {

}

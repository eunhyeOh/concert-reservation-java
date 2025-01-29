package kr.hhplus.be.server.api.concert.domain.dto;

import java.time.LocalDateTime;

public record ScheduleResult(Long scheduleId, Long concertId, Integer maxCapacity, LocalDateTime startDt, LocalDateTime endDt, LocalDateTime createdAt) {
}

package kr.hhplus.be.server.api.concert.domain.dto;

import java.time.LocalDateTime;

public record ConcertResult(Long concertId, String title, String description, LocalDateTime createdAt) {
}

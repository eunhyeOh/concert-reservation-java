package kr.hhplus.be.server.api.queue.domain.dto;

public record QueueResult(Long queueId, String token, Integer waitingNumber) {
}

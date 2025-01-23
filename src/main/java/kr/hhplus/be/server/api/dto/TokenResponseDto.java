package kr.hhplus.be.server.api.dto;

import java.time.LocalDateTime;

public class TokenResponseDto {
    private Long queueId;           // 대기열 항목 고유 식별자
    private Long concertId;         // 콘서트 ID
    private Long scheduleId;        // 콘서트 스케줄 고유 식별자
    private Long userId;            // 사용자 ID
    private String tokenUuid;       // 유저 고유 토큰 (UUID)
    private LocalDateTime queuedAt; // 대기열 등록 시간
    private LocalDateTime expiresAt; // 만료 시간
    private String status;          // 대기 상태 (WAITING 등)
    private int queuePosition;
    private String estimatedWaitTime;

    public TokenResponseDto(Long queueId) {
        this.queueId = queueId;
    }

    public TokenResponseDto(Long queueId, Long concertId, Long scheduleId, Long userId, String tokenUuid, LocalDateTime queuedAt, LocalDateTime expiresAt, String status, int queuePosition, String estimatedWaitTime) {
        this.queueId = queueId;
        this.concertId = concertId;
        this.scheduleId = scheduleId;
        this.userId = userId;
        this.tokenUuid = tokenUuid;
        this.queuedAt = queuedAt;
        this.expiresAt = expiresAt;
        this.status = status;
        this.queuePosition = queuePosition;
        this.estimatedWaitTime = estimatedWaitTime;
    }

    public Long getQueueId() {
        return queueId;
    }

    public void setQueueId(Long queueId) {
        this.queueId = queueId;
    }

    public Long getConcertId() {
        return concertId;
    }

    public void setConcertId(Long concertId) {
        this.concertId = concertId;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTokenUuid() {
        return tokenUuid;
    }

    public void setTokenUuid(String tokenUuid) {
        this.tokenUuid = tokenUuid;
    }

    public LocalDateTime getQueuedAt() {
        return queuedAt;
    }

    public void setQueuedAt(LocalDateTime queuedAt) {
        this.queuedAt = queuedAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getQueuePosition() {
        return queuePosition;
    }

    public void setQueuePosition(int queuePosition) {
        this.queuePosition = queuePosition;
    }

    public String getEstimatedWaitTime() {
        return estimatedWaitTime;
    }

    public void setEstimatedWaitTime(String estimatedWaitTime) {
        this.estimatedWaitTime = estimatedWaitTime;
    }
}

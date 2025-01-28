package kr.hhplus.be.server.api.token.domain.model;

import java.time.Instant;

public class Token {

    private Long queueId;
    private Long concertId;
    private Long userId;
    private String tokenUuid;
    private Instant queuedAt;
    private Instant expiresAt;
    private String status;

    public Token(Long queueId) {
        this.queueId = queueId;
    }

    public Token(Long queueId, Long concertId, Long userId, String tokenUuid, Instant queuedAt, Instant expiresAt, String status) {
        this.queueId = queueId;
        this.concertId = concertId;
        this.userId = userId;
        this.tokenUuid = tokenUuid;
        this.queuedAt = queuedAt;
        this.expiresAt = expiresAt;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Instant getQueuedAt() {
        return queuedAt;
    }

    public void setQueuedAt(Instant queuedAt) {
        this.queuedAt = queuedAt;
    }

    public String getTokenUuid() {
        return tokenUuid;
    }

    public void setTokenUuid(String tokenUuid) {
        this.tokenUuid = tokenUuid;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getConcertId() {
        return concertId;
    }

    public void setConcertId(Long concertId) {
        this.concertId = concertId;
    }

    public Long getQueueId() {
        return queueId;
    }

    public void setQueueId(Long queueId) {
        this.queueId = queueId;
    }
}

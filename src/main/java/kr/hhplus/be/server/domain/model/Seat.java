package kr.hhplus.be.server.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Seat {

    private Long scheduleId;
    private Long seatId;
    private String seatNumber;
    private BigDecimal price;
    private String status;

    public Seat(Long scheduleId, Long seatId, String seatNumber, BigDecimal price, String status) {
        this.scheduleId = scheduleId;
        this.seatId = seatId;
        this.seatNumber = seatNumber;
        this.price = price;
        this.status = status;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Long getSeatId() {
        return seatId;
    }

    public void setSeatId(Long seatId) {
        this.seatId = seatId;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class WaitQueueToken {
        private Long userId;
        private Long concertId;
        private String token;
        private int position;
        private LocalDateTime issuedAt;
        private LocalDateTime expiresAt;

        public WaitQueueToken(Long userId, Long concertId, String token, int position, LocalDateTime issuedAt) {
            this.userId = userId;
            this.concertId = concertId;
            this.token = token;
            this.position = position;
            this.issuedAt = issuedAt;
            this.expiresAt = issuedAt.plusMinutes(5); // Example expiry time
        }
    }
}

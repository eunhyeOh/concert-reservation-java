package kr.hhplus.be.server.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
public class SeatReservationDto {
    private Long seatId;          // 좌석 고유 식별자
    private Long concertId;       // 콘서트 ID
    private Long scheduleId;      // 콘서트 스케줄 고유 식별자
    private String seatNumber;    // 좌석 번호
    private Long userId;          // 사용자 ID
    private LocalDateTime reservedAt;      // 임시 배정 시작 시간
    private LocalDateTime reservedUntil;   // 임시 배정 만료 시간
    private String status;   // 임시 배정 만료 시간
    private BigDecimal price;   // 임시 배정 만료 시간

    public SeatReservationDto(Long seatId, Long concertId, Long scheduleId, String seatNumber, Long userId, LocalDateTime reservedAt, LocalDateTime reservedUntil, String status, BigDecimal price) {
        this.seatId = seatId;
        this.concertId = concertId;
        this.scheduleId = scheduleId;
        this.seatNumber = seatNumber;
        this.userId = userId;
        this.reservedAt = reservedAt;
        this.reservedUntil = reservedUntil;
        this.status = status;
        this.price = price;
    }

    public SeatReservationDto(Long seatId, String seatNumber, BigDecimal price, String status) {
        this.seatId = seatId;
        this.seatNumber = seatNumber;
        this.price = price;
        this.status = status;
    }


    @Override
    public String toString() {
        return "SeatReservationDto{" +
                "seatId=" + seatId +
                ", concertId=" + concertId +
                ", scheduleId=" + scheduleId +
                ", seatNumber='" + seatNumber + '\'' +
                ", userId=" + userId +
                ", reservedAt=" + reservedAt +
                ", reservedUntil=" + reservedUntil +
                ", status=" + status +
                ", price=" + price +
                '}';
    }
}

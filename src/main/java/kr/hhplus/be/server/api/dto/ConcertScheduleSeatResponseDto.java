package kr.hhplus.be.server.api.dto;

import java.math.BigDecimal;
import java.util.List;

public class ConcertScheduleSeatResponseDto {

    private Long concertId;
    private Long scheduleId;
    private String selectedDate;
    private List<SeatDetail> availableSeats;

    public static class SeatDetail{
        private Long seatId;
        private String seatNumber;
        private BigDecimal price;
        private String status;

        public SeatDetail(Long seatId, String seatNumber, BigDecimal price, String status) {
            this.seatId = seatId;
            this.seatNumber = seatNumber;
            this.price = price;
            this.status = status;
        }
    }

    public ConcertScheduleSeatResponseDto(Long concertId, Long scheduleId, String selectedDate, List<SeatDetail> availableSeats) {
        this.concertId = concertId;
        this.scheduleId = scheduleId;
        this.selectedDate = selectedDate;
        this.availableSeats = availableSeats;
    }

    public Long getConcertId() {
        return concertId;
    }


    public Long getScheduleId() {
        return scheduleId;
    }

    public String getSelectedDate() {
        return selectedDate;
    }

    public List<SeatDetail> getAvailableSeats() {
        return availableSeats;
    }
}

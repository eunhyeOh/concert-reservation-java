package kr.hhplus.be.server.api.dto;

import java.util.List;

public class ConcertScheduleResponseDto {

    private Long concertId;
    private List<String> availableDates;

    public ConcertScheduleResponseDto(Long concertId, List<String> availableDates) {
        this.concertId = concertId;
        this.availableDates = availableDates;
    }

    public Long getConcertId() {
        return concertId;
    }

    public List<String> getAvailableDates() {
        return availableDates;
    }
}

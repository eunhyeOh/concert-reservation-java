package kr.hhplus.be.server.api.dto;

public class TokenRequestDto {
    private Long userId;
    private Long concertId;

    public Long getUserId() {
        return userId;
    }

    public Long getConcertId() {
        return concertId;
    }
}

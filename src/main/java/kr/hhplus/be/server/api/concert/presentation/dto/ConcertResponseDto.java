package kr.hhplus.be.server.api.concert.presentation.dto;

import kr.hhplus.be.server.api.concert.domain.entity.Concert;
import lombok.Builder;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Builder
public class ConcertResponseDto {

    private Long concertId;
    private String title;
    private String description;
    private Instant createdAt;

    public static ConcertResponseDto toDto(Concert concert) {
        return ConcertResponseDto.builder()
                .concertId(concert.getId())
                .title(concert.getTitle())
                .description(concert.getDescription())
                .build();
    }

    public static List<ConcertResponseDto> toDtoList(List<Concert> concerts){
        List<ConcertResponseDto> dtos = new ArrayList<>();
        for (Concert concert : concerts) {
            dtos.add(toDto(concert));
        }
        return dtos;
    }
}

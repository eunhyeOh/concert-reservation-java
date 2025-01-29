package kr.hhplus.be.server.api.concert.presentation.dto;

import kr.hhplus.be.server.api.concert.domain.dto.ConcertResult;
import kr.hhplus.be.server.api.concert.domain.entity.Concert;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
public class ConcertResponse {

    private Long concertId;
    private String title;
    private String description;
    private LocalDateTime createdAt;

    public static ConcertResponse toDto(ConcertResult concert) {
        return ConcertResponse.builder()
                .concertId(concert.concertId())
                .title(concert.title())
                .description(concert.description())
                .createdAt(concert.createdAt())
                .build();
    }

    public static List<ConcertResponse> toDtoList(List<ConcertResult> concerts){
        List<ConcertResponse> resultList = new ArrayList<>();
        for (ConcertResult concert : concerts) {
            resultList.add(toDto(concert));
        }
        return resultList;
    }
}

package kr.hhplus.be.server.api.concert.presentation.dto;

import kr.hhplus.be.server.api.concert.domain.entity.Concert;
import kr.hhplus.be.server.api.concert.domain.entity.ConcertSchedule;
import lombok.Builder;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Builder
public class ScheduleResponseDto {

    private Long scheduleId;
    private Long concertId;
    private Integer maxCapacity;
    private Instant startDt;
    private Instant endDt;

    public static ScheduleResponseDto toDto(ConcertSchedule schedule) {
        return ScheduleResponseDto.builder()
                .scheduleId(schedule.getId())
                .concertId(schedule.getConcertId())
                .maxCapacity(schedule.getMaxCapacity())
                .startDt(schedule.getStartDt())
                .endDt(schedule.getEndDt())
                .build();
    }

    public static List<ScheduleResponseDto> toDtoList(List<ConcertSchedule> schedules) {

        List<ScheduleResponseDto> dtos = new ArrayList<>();
        for (ConcertSchedule schedule : schedules) {
            dtos.add(ScheduleResponseDto.toDto(schedule));
        }

        return dtos;
    }
}

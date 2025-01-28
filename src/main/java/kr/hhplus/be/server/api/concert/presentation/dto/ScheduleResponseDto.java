package kr.hhplus.be.server.api.concert.presentation.dto;

import kr.hhplus.be.server.api.concert.domain.entity.Schedule;
import lombok.Builder;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Builder
public class ScheduleResponseDto {

    private Long scheduleId;
    private Long concertId;
    private Integer maxCapacity;
    private Instant date;

    public static ScheduleResponseDto toDto(Schedule schedule) {
        return ScheduleResponseDto.builder()
                .scheduleId(schedule.getScheduleId())
                .concertId(schedule.getConcertId())
                .maxCapacity(schedule.getMaxCapacity())
                .date(schedule.getDate())
                .build();
    }

    public static List<ScheduleResponseDto> toDtoList(List<Schedule> schedules) {

        List<ScheduleResponseDto> dtos = new ArrayList<>();
        for (Schedule schedule : schedules) {
            dtos.add(ScheduleResponseDto.toDto(schedule));
        }

        return dtos;
    }
}

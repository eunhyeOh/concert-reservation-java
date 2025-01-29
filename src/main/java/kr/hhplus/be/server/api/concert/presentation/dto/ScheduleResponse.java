package kr.hhplus.be.server.api.concert.presentation.dto;

import kr.hhplus.be.server.api.concert.domain.dto.ScheduleResult;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
public class ScheduleResponse {

    private Long scheduleId;
    private Long concertId;
    private Integer maxCapacity;
    private LocalDateTime startDt;
    private LocalDateTime endDt;
    private LocalDateTime createdAt;

    //Record -> dto
    public static ScheduleResponse toDto(ScheduleResult schedule) {
        return ScheduleResponse.builder()
                .scheduleId(schedule.scheduleId())
                .concertId(schedule.concertId())
                .maxCapacity(schedule.maxCapacity())
                .createdAt(schedule.createdAt())
                .startDt(schedule.startDt())
                .endDt(schedule.endDt())
                .build();
    }

    public static List<ScheduleResponse> toDtoList(List<ScheduleResult> schedules) {

        List<ScheduleResponse> resultList = new ArrayList<>();
        for (ScheduleResult schedule : schedules) {
            resultList.add(ScheduleResponse.toDto(schedule));
        }

        return resultList;
    }
}

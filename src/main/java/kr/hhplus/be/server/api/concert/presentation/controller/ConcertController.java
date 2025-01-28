package kr.hhplus.be.server.api.concert.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import kr.hhplus.be.server.api.concert.application.ConcertService;
import kr.hhplus.be.server.api.concert.domain.entity.Concert;
import kr.hhplus.be.server.api.concert.domain.entity.ConcertSchedule;
import kr.hhplus.be.server.api.concert.presentation.dto.ConcertResponseDto;
import kr.hhplus.be.server.api.concert.presentation.dto.ScheduleResponseDto;
import kr.hhplus.be.server.api.concert.presentation.dto.SeatResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/concerts")
public class ConcertController {

    private final ConcertService concertService;

    public ConcertController(ConcertService concertService) {
        this.concertService = concertService;
    }

    //Mock 데이터
    private final List<Map<String, Object>> concert = List.of(
            Map.of("concert_id", 1, "title", "DAY6 부산콘", "date", "2025-01-29"),
            Map.of("concert_id", 2, "title", "DAY6 대전콘", "date", "2025-01-30")
    );

    @Operation(summary = "전체 콘서트 조회", description = "콘서트 목록을 조회합니다.")
    @GetMapping("/")
    public ResponseEntity<List<ConcertResponseDto>> getConcerts() {
        List<Concert> concerts = concertService.getConcertList();
        return ResponseEntity.ok(ConcertResponseDto.toDtoList(concerts));
    }

    @Operation(summary = "콘서트 날짜 조회", description = "콘서트 id로 예약가능한 날짜 목록을 조회합니다.")
    @GetMapping("/{concertId}/schedules")
    public ResponseEntity<?> getAvailableSchedules(
            @Parameter(description = "콘서트ID", example = "1") @PathVariable Long concertId,
            @Parameter(description = "인증토큰") @RequestHeader(value = "Authorization", required = false) String token)
    {

        List<ConcertSchedule> scheduleList = concertService.getScheduleList(concertId);
        return ResponseEntity.ok(ScheduleResponseDto.toDtoList(scheduleList));

    }

    @Operation(summary = "스케줄 좌석 조회", description = "스케줄 id로 좌석 목록을 조회합니다.")
    @GetMapping("/{scheduleId}/seats")
    public ResponseEntity<List<SeatResponseDto>> getAvailableSeats(
            @Parameter(description = "스케쥴ID", example = "1") @PathVariable Long scheduleId,
            @Parameter(description = "인증토큰") @RequestHeader(value = "Authorization", required = false) String token)
    {
            //Entity -> DTO 변환
            return ResponseEntity.ok(SeatResponseDto.toDtoList(concertService.getSeatList(scheduleId)));
    }
}

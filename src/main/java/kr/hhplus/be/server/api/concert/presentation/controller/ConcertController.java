package kr.hhplus.be.server.api.concert.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import kr.hhplus.be.server.api.concert.domain.dto.ConcertResult;
import kr.hhplus.be.server.api.concert.domain.dto.ScheduleResult;
import kr.hhplus.be.server.api.concert.domain.service.ConcertService;
import kr.hhplus.be.server.api.concert.presentation.dto.ConcertResponse;
import kr.hhplus.be.server.api.concert.presentation.dto.ScheduleResponse;
import kr.hhplus.be.server.api.concert.presentation.dto.SeatResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/concerts")
public class ConcertController {

    private final ConcertService concertService;

    @Operation(summary = "전체 콘서트 조회", description = "콘서트 목록을 조회합니다.")
    @GetMapping("/")
    public ResponseEntity<List<ConcertResponse>> getConcerts() {
        List<ConcertResult> concerts = concertService.getConcertList();
        return ResponseEntity.ok(ConcertResponse.toDtoList(concerts));
    }

    @Operation(summary = "콘서트 날짜 조회", description = "콘서트 id로 예약가능한 날짜 목록을 조회합니다.")
    @GetMapping("/{concertId}/schedules")
    public ResponseEntity<List<ScheduleResponse>> getAvailableSchedules(
            @Parameter(description = "콘서트ID", example = "1") @PathVariable Long concertId,
            @Parameter(description = "인증토큰") @RequestHeader(value = "Authorization", required = false) String token)
    {

        List<ScheduleResult> scheduleList = concertService.getScheduleList(concertId);
        return ResponseEntity.ok(ScheduleResponse.toDtoList(scheduleList));

    }

    @Operation(summary = "스케줄 좌석 조회", description = "스케줄 id로 좌석 목록을 조회합니다.")
    @GetMapping("/{scheduleId}/seats")
    public ResponseEntity<List<SeatResponse>> getAvailableSeats(
            @Parameter(description = "스케쥴ID", example = "1") @PathVariable Long scheduleId,
            @Parameter(description = "인증토큰") @RequestHeader(value = "Authorization", required = false) String token)
    {
            //Record -> DTO 변환
            return ResponseEntity.ok(SeatResponse.toDtoList(concertService.getSeatList(scheduleId)));
    }
}

package kr.hhplus.be.server.api.v1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import kr.hhplus.be.server.api.dto.ConcertScheduleResponseDto;
import kr.hhplus.be.server.api.dto.ConcertScheduleSeatResponseDto;
import kr.hhplus.be.server.api.dto.ErrorResponseDto;
import kr.hhplus.be.server.application.facade.ConcertFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/concerts")
public class ConcertController {

    private final ConcertFacade concertFacade;

    public ConcertController(ConcertFacade concertFacade) {
        this.concertFacade = concertFacade;
    }

    //Mock 데이터
    private final List<Map<String, Object>> concert = List.of(
            Map.of("concert_id", 1, "title", "DAY6 부산콘", "date", "2025-01-29"),
            Map.of("concert_id", 2, "title", "DAY6 대전콘", "date", "2025-01-30")
    );

    @GetMapping("/test")
    public ResponseEntity<List<Map<String, Object>>> getConcerts() {
        return ResponseEntity.ok(concert);
    }

    @Operation(summary = "예약 가능 날짜 조회", description = "콘서트 id로 예약가능한 날짜 목록을 조회합니다.")
    @GetMapping("/{concertId}/schedules")
    public ResponseEntity<?> getAvailableDates(
            @Parameter(description = "콘서트ID", example = "1") @PathVariable Long concertId,
            @Parameter(description = "인증토큰") @RequestHeader(value = "Authorization", required = false) String token)
    {
        try {
            List<String> avaliableDates = concertFacade.getAvailableDates(concertId, token);

            return ResponseEntity.ok(
                   new ConcertScheduleResponseDto(concertId, avaliableDates)
            );
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(404).body(new ErrorResponseDto("콘서트 정보를 찾을 수 없습니다."));
        } catch (SecurityException e) {
            return ResponseEntity.status(401).body(new ErrorResponseDto("잘못된 토큰 정보입니다."));
        }
    }

    @Operation(summary = "예약 가능 날짜의 좌석 조회", description = "콘서트 id,날짜로 좌석 목록을 조회합니다.")
    @GetMapping("/{concertId}/scheduleSeats")
    public ResponseEntity<?> getavailableSeats(
            @Parameter(description = "콘서트ID", example = "1") @PathVariable Long concertId,
            @Parameter(description = "콘서트날짜", example = "2025-02-01") @PathVariable String date,
            @Parameter(description = "인증토큰") @RequestHeader(value = "Authorization", required = false) String token)
    {
        try {
            //date를 LocalDate로 변환
            LocalDate parsedDate = LocalDate.parse(date);

            ConcertScheduleSeatResponseDto availableSeats = concertFacade.getavailableSeats(concertId, token, parsedDate);
            return ResponseEntity.ok(availableSeats);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(404).body(new ErrorResponseDto("콘서트 정보를 찾을 수 없습니다."));
        } catch (SecurityException e) {
            return ResponseEntity.status(401).body(new ErrorResponseDto("잘못된 토큰 정보입니다."));
        }
    }
}

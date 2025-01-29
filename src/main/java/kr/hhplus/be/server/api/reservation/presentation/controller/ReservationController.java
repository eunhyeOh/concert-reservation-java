package kr.hhplus.be.server.api.reservation.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import kr.hhplus.be.server.api.reservation.domain.service.ReservationService;
import kr.hhplus.be.server.api.reservation.presentation.dto.ReservationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    /**
     * 좌석 예약
     * */
    @Operation(summary = "좌석 임시 예약", description = "좌석을 예약하기 위해 임시로 선점합니다.")
    @PostMapping("/reserve")
    public ResponseEntity<String> reserve(
            @Parameter(description = "유저ID, 좌석ID") @RequestBody ReservationRequest request)
    {
        reservationService.reserveSeat(request.getUserId(), request.getSeatId());
        return ResponseEntity.ok("좌석 임시 예약 성공");
    }


    /**
     * 만료 좌석 상태 변경
     * */
    @PostMapping("/release-expired")
    public ResponseEntity<String> releaseExpiredReservations()
    {
        reservationService.releaseExpiredReservations();

        return ResponseEntity.ok("만료된 좌석 오픈");
    }


    @PostMapping("/confirm")
    public ResponseEntity<String> confirmResevation(@RequestParam Long reservationId)
    {
        reservationService.confirmReservation(reservationId);

        return ResponseEntity.ok("좌석 예약이 완료되었습니다.");
    }
}

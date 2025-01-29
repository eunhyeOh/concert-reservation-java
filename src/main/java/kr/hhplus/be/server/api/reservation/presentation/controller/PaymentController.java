package kr.hhplus.be.server.api.reservation.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import kr.hhplus.be.server.api.reservation.application.facade.PaymentFacade;
import kr.hhplus.be.server.api.reservation.domain.dto.PaymentResult;
import kr.hhplus.be.server.api.reservation.presentation.dto.PaymentRequest;
import kr.hhplus.be.server.api.reservation.presentation.dto.PaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentFacade paymentFacade;

    @Operation(summary = "콘서트 결제", description = "콘서트 좌석을 결제하고 토큰을 만료합니다.")
    @PostMapping
    public ResponseEntity<PaymentResponse> processPayment (
         @Parameter(description = "유저ID, 예약ID") @RequestBody PaymentRequest request,
         @Parameter(description = "인증토큰") @RequestHeader(value = "Authorization", required = false) String token)
    {

        PaymentResult result = paymentFacade.processPayment(request.getUserId(), request.getReservationId(), token);
        return ResponseEntity.ok(PaymentResponse.toDto(result));
    }
}

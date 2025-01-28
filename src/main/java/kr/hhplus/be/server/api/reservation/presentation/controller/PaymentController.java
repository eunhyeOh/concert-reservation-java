package kr.hhplus.be.server.api.reservation.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import kr.hhplus.be.server.api.reservation.application.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Operation(summary = "콘서트 결제", description = "콘서트 좌석을 결제하고 토큰을 만료한다.")
    @PostMapping("/complete")
    public ResponseEntity<?> complete (){

        return ResponseEntity.ok().build();
    }
}

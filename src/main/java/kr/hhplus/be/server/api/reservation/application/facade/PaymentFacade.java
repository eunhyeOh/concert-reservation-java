package kr.hhplus.be.server.api.reservation.application.facade;

import kr.hhplus.be.server.api.reservation.presentation.dto.PaymentRequestDto;
import kr.hhplus.be.server.api.reservation.presentation.dto.PaymentResponseDto;
import kr.hhplus.be.server.api.reservation.application.service.PaymentService;
import kr.hhplus.be.server.api.reservation.application.service.ReservationService;
import kr.hhplus.be.server.api.token.application.usecase.ValidateTokenUseCase;
import org.springframework.stereotype.Component;

@Component
public class PaymentFacade {

    private final ValidateTokenUseCase tokenValidator;
    private final PaymentService paymentService;
    private final ReservationService reservationService;

    public PaymentFacade(ValidateTokenUseCase tokenValidator,
                         PaymentService paymentService,
                         ReservationService reservationService) {
        this.tokenValidator = tokenValidator;
        this.paymentService = paymentService;
        this.reservationService = reservationService;
    }

    public PaymentResponseDto processPayment(Long userId, String token, PaymentRequestDto request) {
        if(!tokenValidator.isValid(token)) {
            throw new IllegalArgumentException("잘못된 토큰 정보입니다.");
        }

//        if(reservationService){
//
//        }
        //잔액 조회
        //잔액 충분한지 확인
        //부족하면 예외처리
        //맞으면 차감진행
        //차감완료 후 토큰 만료, 대기열 업데이트
        return paymentService.processPayment(userId, request.getSeatId(), request.getAmount());
    }
}

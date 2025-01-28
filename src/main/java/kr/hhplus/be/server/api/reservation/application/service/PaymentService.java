package kr.hhplus.be.server.api.reservation.application.service;

import kr.hhplus.be.server.api.reservation.presentation.dto.PaymentResponseDto;
import kr.hhplus.be.server.api.token.application.usecase.ValidateTokenUseCase;
import kr.hhplus.be.server.api.user.domain.repository.UserWalletRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PaymentService {

    private final ValidateTokenUseCase tokenValidator;
    private final UserWalletRepository userWalletRepository;

    public PaymentService(ValidateTokenUseCase tokenValidator, UserWalletRepository userWalletRepository) {
        this.tokenValidator = tokenValidator;
        this.userWalletRepository = userWalletRepository;
    }

    public PaymentResponseDto processPayment(Long userId, Long seatId, BigDecimal amount) {

        //잔액 차감
        Money paymentAmount = Money.of(amount);
        Money remainingBalance  = userWalletRepository.deduct(userId, paymentAmount);
        return new PaymentResponseDto("SUCCESS", "결제가 완료되었습니다.");
    }
}

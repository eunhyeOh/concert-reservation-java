package kr.hhplus.be.server.api.user.application.facade;

import kr.hhplus.be.server.api.user.presentation.dto.BalanceResponseDto;
import kr.hhplus.be.server.api.user.application.service.UserBalanceService;
import kr.hhplus.be.server.api.token.application.usecase.ValidateTokenUseCase;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class UserBalanceFacade {

    private final ValidateTokenUseCase tokenValidator;
    private final UserBalanceService userBalanceService;

    public UserBalanceFacade(ValidateTokenUseCase tokenValidator,
                             UserBalanceService userBalanceService) {
        this.tokenValidator = tokenValidator;
        this.userBalanceService = userBalanceService;
    }

    public BalanceResponseDto getBalance(Long userId, String token) {
        //토큰 검증
        if(!tokenValidator.isValid(token)) {
            throw new IllegalArgumentException("잘못된 토큰 정보입니다.");
        }
        return userBalanceService.getBalance(userId);
    }


    public BalanceResponseDto chargeBalance(Long userId, String token, BigDecimal amount) {
        //토큰 검증
        if(!tokenValidator.isValid(token)) {
            throw new IllegalArgumentException("잘못된 토큰 정보입니다.");
        }
        return userBalanceService.chargeBalance(userId, amount);
    }
}

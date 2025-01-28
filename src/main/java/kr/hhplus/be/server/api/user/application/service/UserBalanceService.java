package kr.hhplus.be.server.api.user.application.service;

import kr.hhplus.be.server.api.user.presentation.dto.BalanceResponseDto;
import kr.hhplus.be.server.api.user.application.usecase.BalanceUseCase;
import kr.hhplus.be.server.api.user.domain.repository.UserWalletRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserBalanceService {

    private final BalanceUseCase balanceUseCase;
    private final UserWalletRepository userWalletRepository;

    public UserBalanceService(BalanceUseCase balanceUseCase,
                              UserWalletRepository userWalletRepository) {
        this.balanceUseCase = balanceUseCase;
        this.userWalletRepository = userWalletRepository;
    }

    /**
     * 사용자 잔액 조회 처리
     * @param userId 사용자 식별자
     * @return BalanceResponse
     */
    public BalanceResponseDto getBalance(Long userId) {

        balanceUseCase.execute(userId);

        Money balance = userWalletRepository.getBalanceByUserId(userId);
        return new BalanceResponseDto(userId, balance.getValue());
    }

    /**
     * 사용자 잔액 충전 처리
     * @param userId 사용자 식별자
     * @param amount 충전 금액
     * @return BalanceResponse
     */
    public BalanceResponseDto chargeBalance(Long userId, BigDecimal amount) {

        balanceUseCase.execute(userId);

        Money newBalance = userWalletRepository.charge(userId, Money.of(amount));
        return new BalanceResponseDto(userId, newBalance.getValue());
    }
}

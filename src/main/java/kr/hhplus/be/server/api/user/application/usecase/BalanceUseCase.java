package kr.hhplus.be.server.api.user.application.usecase;

import kr.hhplus.be.server.api.user.infrastructure.repository.UserWalletJpaRepository;
import org.springframework.stereotype.Component;

@Component
public class BalanceUseCase {

    private UserWalletJpaRepository userWalletJpaRepository;

    public BalanceUseCase(UserWalletJpaRepository userWalletJpaRepository) {
        this.userWalletJpaRepository = userWalletJpaRepository;
    }

    //없으면 inset하도록 변경하기
    public void execute(Long userId) {
        if(!userWalletJpaRepository.existsByUserId(userId)){
            throw new IllegalArgumentException("사용자 정보를 찾을 수 없습니다.");
        }
    }
}

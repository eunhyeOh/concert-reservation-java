package kr.hhplus.be.server.api.user.application.usecase;

import kr.hhplus.be.server.api.user.infrastructure.repository.UserJpaRepository;
import org.springframework.stereotype.Component;

@Component
public class ValidateUserUseCase {

    private final UserJpaRepository userJpaRepository;

    public ValidateUserUseCase(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    public void execute(Long userId) {
        if (!userJpaRepository.existsByUserId(userId)) {
            throw new IllegalArgumentException("사용자 정보를 찾을 수 없습니다.");
        }
    }
}

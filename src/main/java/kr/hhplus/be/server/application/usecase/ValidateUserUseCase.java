package kr.hhplus.be.server.application.usecase;

import kr.hhplus.be.server.application.service.UserService;
import kr.hhplus.be.server.infrastructure.persistence.repository.UserJpaRepository;
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

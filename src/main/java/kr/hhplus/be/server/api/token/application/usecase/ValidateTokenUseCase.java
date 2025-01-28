package kr.hhplus.be.server.api.token.application.usecase;

import kr.hhplus.be.server.api.token.domain.model.Token;
import kr.hhplus.be.server.api.token.domain.repository.TokenRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ValidateTokenUseCase {

    private final TokenRepository tokenRepository;

    public ValidateTokenUseCase(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public Optional<Token> execute(Long concertId, Long userId) {
        return tokenRepository.findByUserIdAndConcertId(userId, concertId);
    }

    /**
     * DB를 통해 토큰의 유효성 검증
     * @param token 클라이언트에서 전달받은 토큰
     * @return true: 유효한 토큰, false: 무효한 토큰
     */
    public boolean isValid(String token) {
        return tokenRepository.findByToken(token)
                .map(waitQueue -> "ACTIVE".equals(waitQueue.getStatus()))
                .orElse(false);
    }
}

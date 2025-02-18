package kr.hhplus.be.server.application.service;

import kr.hhplus.be.server.api.dto.TokenRequestDto;
import kr.hhplus.be.server.api.dto.TokenResponseDto;
import kr.hhplus.be.server.application.usecase.ValidateConcertUseCase;
import kr.hhplus.be.server.application.usecase.ValidateTokenUseCase;
import kr.hhplus.be.server.application.usecase.ValidateUserUseCase;
import kr.hhplus.be.server.domain.model.Token;
import kr.hhplus.be.server.domain.repository.TokenRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class TokenService {

    private final ValidateUserUseCase validateUserUseCase;
    private final ValidateConcertUseCase validateConcertUseCase;
    private final ValidateTokenUseCase validateTokenUseCase;
    private final TokenRepository tokenRepository;

    public TokenService(ValidateUserUseCase validateUserUseCase,
                        ValidateConcertUseCase validateConcertUseCase,
                        ValidateTokenUseCase validateTokenUseCase,
                        TokenRepository tokenRepository) {
        this.validateUserUseCase = validateUserUseCase;
        this.validateConcertUseCase = validateConcertUseCase;
        this.validateTokenUseCase = validateTokenUseCase;
        this.tokenRepository = tokenRepository;
    }

    //token 생성 및 반환
    public TokenResponseDto issueToken(Long concertId, Long userId) {

        validateUserUseCase.execute(userId);
        validateConcertUseCase.execute(concertId);

        Token token;
        token = validateTokenUseCase.execute(concertId, userId)
                .orElse(null);

        if(token == null) {
            String uuid = UUID.randomUUID().toString();
            token = tokenRepository.addToQueue(concertId, userId, uuid);
        }

        return new TokenResponseDto(token.getQueueId());
    }
}

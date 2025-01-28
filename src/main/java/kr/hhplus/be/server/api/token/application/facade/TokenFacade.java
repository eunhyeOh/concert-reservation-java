package kr.hhplus.be.server.api.token.application.facade;

import kr.hhplus.be.server.api.token.presentation.dto.TokenRequestDto;
import kr.hhplus.be.server.api.token.presentation.dto.TokenResponseDto;
import kr.hhplus.be.server.api.token.application.service.TokenService;
import org.springframework.stereotype.Component;

@Component
public class TokenFacade {
    private final TokenService tokenService;

    public TokenFacade(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    public TokenResponseDto issueToken(TokenRequestDto request){
        TokenResponseDto response = tokenService.issueToken(request.getConcertId(), request.getUserId());

        if(response == null || response.getTokenUuid() == null){
            throw new IllegalArgumentException("토큰 발급에 실패했습니다.");
        }

        return response;
    }
}

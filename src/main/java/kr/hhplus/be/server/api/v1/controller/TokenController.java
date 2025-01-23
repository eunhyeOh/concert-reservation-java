package kr.hhplus.be.server.api.v1.controller;

import kr.hhplus.be.server.api.dto.TokenRequestDto;
import kr.hhplus.be.server.api.dto.TokenResponseDto;
import kr.hhplus.be.server.application.facade.TokenFacade;
import kr.hhplus.be.server.application.service.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/token")
public class TokenController {

    private final TokenFacade tokenFacade;

    private TokenController(TokenFacade tokenFacade) {
        this.tokenFacade = tokenFacade;
    }


    @PostMapping("/issue")
    public ResponseEntity<?> issueToken(@RequestBody TokenRequestDto request) {
        try{
            TokenResponseDto reponse = tokenFacade.issueToken(request);
            return ResponseEntity.ok(reponse);

        }catch (IllegalArgumentException e){
            return ResponseEntity.status(404).body(Map.of("error", e.getMessage()));
        }
    }

}

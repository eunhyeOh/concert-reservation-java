package kr.hhplus.be.server.application.facade;

import kr.hhplus.be.server.api.dto.ConcertScheduleSeatResponseDto;
import kr.hhplus.be.server.application.service.ConcertService;
import kr.hhplus.be.server.application.usecase.ValidateTokenUseCase;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class ConcertFacade {

    private final ValidateTokenUseCase tokenValidator;
    private final ConcertService concertService;

    public ConcertFacade(ValidateTokenUseCase tokenValidator,
                         ConcertService concertService) {
        this.tokenValidator = tokenValidator;
        this.concertService = concertService;
    }

    public List<String> getAvailableDates(Long concertId, String token) {
        if(!tokenValidator.isValid(token)) {
            throw new IllegalArgumentException("잘못된 토큰 정보입니다.");
        }

        //서비스 호출
        return concertService.getAvailableDates(concertId);
    }


    public ConcertScheduleSeatResponseDto getavailableSeats(Long concertId, String token, LocalDate date) {
        if(!tokenValidator.isValid(token)) {
            throw new IllegalArgumentException("잘못된 토큰 정보입니다.");
        }

        return concertService.getAvailableSeats(concertId, date);
    }
}

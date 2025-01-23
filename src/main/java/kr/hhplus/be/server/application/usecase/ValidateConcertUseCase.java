package kr.hhplus.be.server.application.usecase;

import kr.hhplus.be.server.domain.repository.ConcertRepository;
import org.springframework.stereotype.Component;

@Component
public class ValidateConcertUseCase {

    private final ConcertRepository concertRepository;

    public ValidateConcertUseCase(ConcertRepository concertRepository) {
        this.concertRepository = concertRepository;
    }

    public void execute(Long concertId) {
        if(!concertRepository.existsById(concertId)){
            throw new IllegalArgumentException("콘서트 정보를 찾을 수 없습니다.");
        }
    }
}

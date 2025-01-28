package kr.hhplus.be.server.api.token.domain.repository;

import kr.hhplus.be.server.api.token.domain.model.Token;
import kr.hhplus.be.server.api.token.infrastructure.entity.WaitingQueue;

import java.util.Optional;

public interface TokenRepository {
    Optional<Token> findByUserIdAndConcertId(Long concertId, Long userId); //대기열 토큰 존재 여부 확인

    boolean existsByToken(Long userId, Long concertId);

    Token addToQueue(Long userId, Long concertId, String token); // 대기열 추가
    Optional<Integer> getQueuePosition(String token); // 대기열 순번 조회

    Optional<WaitingQueue> findByToken(String token);

    void expireToken(String token);
}

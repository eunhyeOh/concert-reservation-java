package kr.hhplus.be.server.api.token.infrastructure.repository;

import kr.hhplus.be.server.api.token.domain.model.Token;
import kr.hhplus.be.server.api.token.domain.repository.TokenRepository;
import kr.hhplus.be.server.api.token.infrastructure.entity.WaitingQueue;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * 구체적인 데이터 접근 구현
 * */
@Repository
public class TokenRepositoryImpl implements TokenRepository {
    private final TokenJpaRepository tokenJpaRepository;

    public TokenRepositoryImpl(TokenJpaRepository tokenJpaRepository) {
        this.tokenJpaRepository = tokenJpaRepository;
    }

    @Override
    public Optional<Token> findByUserIdAndConcertId(Long userId, Long concertId) {
        Optional<WaitingQueue> dbResult = tokenJpaRepository.existsByUserIdAndConcertId(concertId, userId);

        //DB Entity -> Domain Model
        return dbResult.map(dbEntity -> new Token (dbEntity.getQueueId()));
    }

    @Override
    public boolean existsByToken(Long userId, Long concertId) {
        return false;
    }

    @Override
    public Token addToQueue(Long concertId, Long userId, String uuid) {
        WaitingQueue waitingQueue = new WaitingQueue();
        waitingQueue.setConcertId(concertId);
        waitingQueue.setUserId(userId);
        waitingQueue.setTokenUuid(uuid);
        waitingQueue.setQueuedAt(Instant.from(LocalDateTime.now()));
        waitingQueue.setStatus("WAITING");

        WaitingQueue save =  tokenJpaRepository.save(waitingQueue);

        return new Token (save.getQueueId());

        // return tokenJpaRepository.countByConcertIdAndStatus(concertId, "WAITING");
    }

    @Override
    public Optional<Integer> getQueuePosition(String token) {
        Optional<WaitingQueue> queue = tokenJpaRepository.findByTokenUuid(token);
        return queue.map(q -> tokenJpaRepository.countByConcertIdAndStatus(q.getConcertId(), "WAITING"));
    }

    @Override
    public Optional<WaitingQueue> findByToken(String token) {
        return Optional.empty();
    }

    @Override
    @Transactional
    public void expireToken(String token){
        WaitingQueue queue = tokenJpaRepository.findByTokenUuid(token)
                .orElseThrow(() -> new IllegalArgumentException("토큰이 존재하지 않습니다."));
        queue.setStatus("EXPIRED");
        queue.setExpiresAt(Instant.now());
        tokenJpaRepository.save(queue);
    }
}

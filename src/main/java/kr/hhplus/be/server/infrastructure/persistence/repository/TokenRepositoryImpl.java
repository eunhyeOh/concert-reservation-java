package kr.hhplus.be.server.infrastructure.persistence.repository;

import kr.hhplus.be.server.domain.model.Token;
import kr.hhplus.be.server.domain.repository.TokenRepository;
import kr.hhplus.be.server.infrastructure.persistence.entity.TbWaitingQueue;
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
        Optional<TbWaitingQueue> dbResult = tokenJpaRepository.existsByUserIdAndConcertId(concertId, userId);

        //DB Entity -> Domain Model
        return dbResult.map(dbEntity -> new Token (dbEntity.getQueueId()));
    }

    @Override
    public boolean existsByToken(Long userId, Long concertId) {
        return false;
    }

    @Override
    public Token addToQueue(Long concertId, Long userId, String uuid) {
        TbWaitingQueue waitingQueue = new TbWaitingQueue();
        waitingQueue.setConcertId(concertId);
        waitingQueue.setUserId(userId);
        waitingQueue.setTokenUuid(uuid);
        waitingQueue.setQueuedAt(Instant.from(LocalDateTime.now()));
        waitingQueue.setStatus("WAITING");

        TbWaitingQueue save =  tokenJpaRepository.save(waitingQueue);

        return new Token (save.getQueueId());

        // return tokenJpaRepository.countByConcertIdAndStatus(concertId, "WAITING");
    }

    @Override
    public Optional<Integer> getQueuePosition(String token) {
        Optional<TbWaitingQueue> queue = tokenJpaRepository.findByTokenUuid(token);
        return queue.map(q -> tokenJpaRepository.countByConcertIdAndStatus(q.getConcertId(), "WAITING"));
    }

    @Override
    public Optional<TbWaitingQueue> findByToken(String token) {
        return Optional.empty();
    }

    @Override
    @Transactional
    public void expireToken(String token){
        TbWaitingQueue queue = tokenJpaRepository.findByTokenUuid(token)
                .orElseThrow(() -> new IllegalArgumentException("토큰이 존재하지 않습니다."));
        queue.setStatus("EXPIRED");
        queue.setExpiresAt(Instant.now());
        tokenJpaRepository.save(queue);
    }
}

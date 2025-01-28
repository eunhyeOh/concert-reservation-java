package kr.hhplus.be.server.api.token.infrastructure.repository;

import kr.hhplus.be.server.api.token.infrastructure.entity.WaitingQueue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * JPA 인터페이스 : DB통신, JPA기반 데이터 접근 기본 메소드 제공
 * */
@Repository
public interface TokenJpaRepository extends JpaRepository<WaitingQueue, Long> {

    @Query("SELECT tokenUuid " +
            "FROM WaitingQueue " +
            "WHERE userId = :userId AND concertId = :concertID AND status = 'WAITING'")
    Optional<WaitingQueue> existsByUserIdAndConcertId(
            @Param("userId") Long userId,
            @Param("ConcertId") Long concertId);

    int countByConcertIdAndStatus(Long concertId, String status); // 대기열 상태별 개수

    Optional<WaitingQueue> findByTokenUuid(String token); // 토큰으로 대기열 조회
}

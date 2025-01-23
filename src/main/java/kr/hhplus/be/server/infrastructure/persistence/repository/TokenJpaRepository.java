package kr.hhplus.be.server.infrastructure.persistence.repository;

import kr.hhplus.be.server.infrastructure.persistence.entity.TbWaitingQueue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * JPA 인터페이스 : DB통신, JPA기반 데이터 접근 기본 메소드 제공
 * */
@Repository
public interface TokenJpaRepository extends JpaRepository<TbWaitingQueue, Long> {

    @Query("SELECT tokenUuid " +
            "FROM TbWaitingQueue " +
            "WHERE userId = :userId AND concertId = :concertID AND status = 'WAITING'")
    Optional<TbWaitingQueue> existsByUserIdAndConcertId(
            @Param("userId") Long userId,
            @Param("ConcertId") Long concertId);

    int countByConcertIdAndStatus(Long concertId, String status); // 대기열 상태별 개수

    Optional<TbWaitingQueue> findByTokenUuid(String token); // 토큰으로 대기열 조회
}

package kr.hhplus.be.server.api.queue.domain.repository;

import kr.hhplus.be.server.api.queue.domain.entity.Queue;
import kr.hhplus.be.server.api.queue.domain.entity.QueueStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface QueueJpaRepository extends JpaRepository<Queue, Long> {

    //대기열 조회
    Optional<Queue> findByToken(String token);

    //QueueId보다 작은 대기열 중에서 특정 상태를 가진 행의 개수
    int countByIdLessThanAndStatus(Long queueId, QueueStatus status);

    //만료된 대기열 상태 변경
    List<Queue> findAllByExpiredDtBeforeAndStatus(LocalDateTime expiredDt, QueueStatus status);
}

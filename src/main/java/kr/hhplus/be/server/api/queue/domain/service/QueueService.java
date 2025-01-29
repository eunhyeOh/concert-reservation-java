package kr.hhplus.be.server.api.queue.domain.service;

import kr.hhplus.be.server.api.queue.domain.dto.QueueResult;
import kr.hhplus.be.server.api.queue.domain.entity.Queue;
import kr.hhplus.be.server.api.queue.domain.entity.QueueStatus;
import kr.hhplus.be.server.api.queue.domain.repository.QueueJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class QueueService {

    private final QueueJpaRepository queueJpaRepository;

    /**
     * 대기열 토큰 생성
     * */
    @Transactional
    public QueueResult createQueueEntry(Long userId) {

        //토큰 생성
        String token = UUID.randomUUID().toString();

        //만료 시간 설정(현재시간+5분)
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiredAt = now.plusSeconds(300);

        //Queue 엔티티 생성
        Queue queue = new Queue(
                userId,
                token,
                QueueStatus.WAITING,
                now,
                expiredAt
        );

        //토큰 저장
        queue = queueJpaRepository.save(queue);

        //대기 순서 계산
        int waitingNumber = queueJpaRepository.countByIdLessThanAndStatus(queue.getId(), QueueStatus.WAITING);

        return new QueueResult(queue.getId(), queue.getToken(), waitingNumber);
    }

    /**
     * 대기열 조회
     * */
    public QueueResult vaildateToken(String token) {
        //토큰으로 대기열 조회
        Queue queue = queueJpaRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 토큰입니다."));

        //현재 시간과 만료시간 비교
        if(queue.getEnteredDt().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("토큰이 만료되었습니다.");
        }

        //대기 순서 계산
        int waitingNumber = queueJpaRepository.countByIdLessThanAndStatus(queue.getId(), QueueStatus.WAITING);

        return new QueueResult(queue.getId(), queue.getToken(), waitingNumber);

    }

    /**
     * 토큰 만료처리
     * */
    @Transactional
    public void expireToken(String token){
        Queue queue = queueJpaRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 토큰입니다."));
        queue.expire();
        queueJpaRepository.save(queue);
    }


    /**
     * 오래된 토큰 만료처리
     * */
    @Transactional
    public void expireOldTokens() {
        //현재 시간 이전의 만료된 대기열 만료처리
        List<Queue> queueList = queueJpaRepository.findAllByExpiredDtBeforeAndStatus(LocalDateTime.now(), QueueStatus.WAITING);

        for(Queue queue : queueList) {
            queue.expire();
        }
    }
}

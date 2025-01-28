package kr.hhplus.be.server.api.queue.application.service;

import jakarta.transaction.Transactional;
import kr.hhplus.be.server.api.queue.domain.entity.Queue;
import kr.hhplus.be.server.api.queue.domain.entity.QueueStatus;
import kr.hhplus.be.server.api.queue.domain.repository.QueueJpaRepository;
import kr.hhplus.be.server.api.queue.presentation.dto.QueueRequestDto;
import kr.hhplus.be.server.api.queue.presentation.dto.QueueResponseDto;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class QueueService {

    private final QueueJpaRepository queueJpaRepository;

    public QueueService(QueueJpaRepository queueJpaRepository) {
        this.queueJpaRepository = queueJpaRepository;
    }

    /**
     * 대기열 토큰 생성
     * */
    @Transactional
    public QueueResponseDto createQueueEntry(QueueRequestDto queueRequestDto) {

        //토큰 생성
        String token = UUID.randomUUID().toString();

        //만료 시간 설정(현재시간+5분)
        Instant now = Instant.now();
        Instant expiredAt = now.plusSeconds(300);

        //Queue 엔티티 생성
        Queue queue = new Queue(
                queueRequestDto.getUserId(),
                token,
                QueueStatus.WAITING,
                now,
                expiredAt
        );

        //토큰 저장
        queue = queueJpaRepository.save(queue);

        //대기 순서 계산
        int waitingNumber = queueJpaRepository.countByIdLessThanAndStatus(queue.getId(), QueueStatus.WAITING);

        return QueueResponseDto.toDto(queue, waitingNumber);
    }

    /**
     * 대기열 조회
     * */
    public QueueResponseDto vaildateToken(String token) {
        //토큰으로 대기열 조회
        Queue queue = queueJpaRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 토큰입니다."));

        //현재 시간과 만료시간 비교
        if(queue.getEnteredDt().isBefore(Instant.now())) {
            throw new IllegalArgumentException("토큰이 만료되었습니다.");
        }

        //대기 순서 계산
        int waitingNumber = queueJpaRepository.countByIdLessThanAndStatus(queue.getId(), QueueStatus.WAITING);

        return QueueResponseDto.toDto(queue, waitingNumber);

    }

    /**
     * 토큰 만료처리
     * */
    public void expireOldTokens() {
        //현재 시간 이전의 만료된 대기열 만료처리
        Instant now = Instant.now();
        queueJpaRepository.deleteByExpiredDtBeforeAndStatus(now, QueueStatus.WAITING);
    }
}

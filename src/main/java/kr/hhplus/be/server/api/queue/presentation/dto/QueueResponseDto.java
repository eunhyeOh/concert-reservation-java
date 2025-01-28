package kr.hhplus.be.server.api.queue.presentation.dto;

import kr.hhplus.be.server.api.queue.domain.entity.Queue;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Builder
public class QueueResponseDto {

    private Long queueId;
    private String token;

    private Integer waitingNumber; //남은 순서

    public static QueueResponseDto toDto(Queue queue, int waitingNumber) {
        return QueueResponseDto.builder()
                .queueId(queue.getId())
                .token(queue.getToken())
                .waitingNumber(waitingNumber)
                .build();

    }
}

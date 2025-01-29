package kr.hhplus.be.server.api.queue.presentation.dto;

import kr.hhplus.be.server.api.queue.domain.dto.QueueResult;
import kr.hhplus.be.server.api.queue.domain.entity.Queue;
import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
public class QueueResponse {

    private Long queueId;
    private String token;

    private Integer waitingNumber; //남은 순서

    //Record -> Dto
    public static QueueResponse toDto(QueueResult queue) {
        return QueueResponse.builder()
                .queueId(queue.queueId())
                .token(queue.token())
                .waitingNumber(queue.waitingNumber())
                .build();

    }
}

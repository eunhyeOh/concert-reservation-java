package kr.hhplus.be.server.api.queue.presentation.dto;

import kr.hhplus.be.server.api.queue.domain.entity.QueueStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QueueRequest {

    private Long userId;
    private String token;
    private QueueStatus status;

}

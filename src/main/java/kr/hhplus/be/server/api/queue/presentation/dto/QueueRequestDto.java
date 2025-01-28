package kr.hhplus.be.server.api.queue.presentation.dto;

import kr.hhplus.be.server.api.queue.domain.entity.QueueStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class QueueRequestDto {

    private Long userId;
    private String token;
    private QueueStatus status;

}

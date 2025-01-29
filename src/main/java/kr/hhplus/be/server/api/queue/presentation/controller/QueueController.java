package kr.hhplus.be.server.api.queue.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import kr.hhplus.be.server.api.queue.domain.dto.QueueResult;
import kr.hhplus.be.server.api.queue.domain.service.QueueService;
import kr.hhplus.be.server.api.queue.presentation.dto.QueueRequest;
import kr.hhplus.be.server.api.queue.presentation.dto.QueueResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/queue")
public class QueueController {

    private final QueueService queueService;

    @Operation(summary = "토큰 발급", description = "uuid토큰을 발급하고, 대기 순번을 반환합니다.")
    @PostMapping("/issue")
    public ResponseEntity<QueueResponse> issueToken(
            @Parameter(description = "유저ID") @RequestBody QueueRequest queueRequest)
    {
        QueueResult result = queueService.createQueueEntry(queueRequest.getUserId());
        return ResponseEntity.ok(QueueResponse.toDto(result));
    }

    @Operation(summary = "토큰 유효성 검증", description = "토큰 유효성 검증하고, 대기 순번을 반환합니다.")
    @GetMapping("/validate")
    public ResponseEntity<QueueResponse> vaildateToken(
            @Parameter(description = "토큰") @RequestHeader("Authorization") String token)
    {
        QueueResult result = queueService.vaildateToken(token);
        return ResponseEntity.ok(QueueResponse.toDto(result));
    }

    @Operation(summary = "토큰 만료처리", description = "일정 기간이 지난 토큰들을 일괄 만료 처리합니다.")
    @PatchMapping("/expire")
    public ResponseEntity<String> expireOldTokens()
    {

        queueService.expireOldTokens();

        return ResponseEntity.ok("Expired tokens processed");
    }
}

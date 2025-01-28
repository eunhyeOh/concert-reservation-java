package kr.hhplus.be.server.api.queue.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import kr.hhplus.be.server.api.queue.application.service.QueueService;
import kr.hhplus.be.server.api.queue.presentation.dto.QueueRequestDto;
import kr.hhplus.be.server.api.queue.presentation.dto.QueueResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/queue")
public class QueueController {

    private final QueueService queueService;

    public QueueController(QueueService queueService) {
        this.queueService = queueService;
    }

    @Operation(summary = "토큰 발급", description = "유저id로 uuid 토큰을 발급하고, 대기 순번을 반환합니다.")
    @PostMapping("/issue")
    public ResponseEntity<QueueResponseDto> issueToken(
            @Parameter(description = "userid") @RequestBody QueueRequestDto queueRequestDto
    ){
        QueueResponseDto responseDto = queueService.createQueueEntry(queueRequestDto);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "토큰 유효성 검증", description = "토큰 유효성 검증하고, 대기 순번을 반환합니다.")
    @GetMapping("/validate")
    public ResponseEntity<QueueResponseDto> vaildateToken(
            @Parameter(description = "토큰") @RequestHeader("Authorization") String token
    ){
        QueueResponseDto responseDto = queueService.vaildateToken(token);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "토큰 만료처리")
    @PatchMapping("expire")
    public ResponseEntity<String> expireOldTokens(){
        queueService.expireOldTokens();
        return ResponseEntity.ok("Expired tokens processed");
    }
}

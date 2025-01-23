package kr.hhplus.be.server.api.dto;

import lombok.*;

@Data
@AllArgsConstructor
public class ErrorResponseDto {
    private String error;

    // 기본 생성자
    public ErrorResponseDto() {
    }
}

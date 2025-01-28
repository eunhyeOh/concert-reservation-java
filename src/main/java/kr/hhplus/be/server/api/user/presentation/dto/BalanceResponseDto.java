package kr.hhplus.be.server.api.user.presentation.dto;

import java.math.BigDecimal;

public class BalanceResponseDto {

    private Long userId;

    private BigDecimal money;

    public BalanceResponseDto(Long userId, BigDecimal money) {
        this.userId = userId;
        this.money = money;
    }
}

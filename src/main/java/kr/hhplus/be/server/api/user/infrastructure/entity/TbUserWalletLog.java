package kr.hhplus.be.server.api.user.infrastructure.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "tb_user_wallet_log", schema = "hhplus", indexes = {
        @Index(name = "idx_wallet_id", columnList = "wallet_id"),
        @Index(name = "idx_user_id", columnList = "user_id")
})
public class TbUserWalletLog {
    @Id
    @Column(name = "log_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "wallet_id", nullable = false)
    private Long walletId;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @NotNull
    @Column(name = "change_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal changeAmount;

    @Size(max = 10)
    @NotNull
    @Column(name = "log_type", nullable = false, length = 10)
    private String logType;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;

}
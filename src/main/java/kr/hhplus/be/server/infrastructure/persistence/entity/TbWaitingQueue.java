package kr.hhplus.be.server.infrastructure.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "tb_waiting_queue", schema = "hhplus", indexes = {
        @Index(name = "idx_concert_id_schedule_id_user_id", columnList = "concert_id, user_id"),
        @Index(name = "idx_concert_id_schedule_id_status", columnList = "concert_id, status"),
        @Index(name = "idx_queued_at", columnList = "queued_at")
}, uniqueConstraints = {
        @UniqueConstraint(name = "uniq_concert_user", columnNames = {"concert_id", "user_id"}),
        @UniqueConstraint(name = "token_uuid", columnNames = {"token_uuid"})
})
public class TbWaitingQueue {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY) // 또는 적합한 전략 선택
    @Column(name = "queue_id", nullable = false)
    private Long queueId;

    @NotNull
    @Column(name = "concert_id", nullable = false)
    private Long concertId;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Size(max = 36)
    @NotNull
    @Column(name = "token_uuid", nullable = false, length = 36)
    private String tokenUuid;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "queued_at")
    private Instant queuedAt;

    @Column(name = "expires_at")
    private Instant expiresAt;

    @Size(max = 10)
    @NotNull
    @ColumnDefault("'WAITING'")
    @Column(name = "status", nullable = false, length = 10)
    private String status;

}
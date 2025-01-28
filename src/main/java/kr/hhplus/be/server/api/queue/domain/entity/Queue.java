package kr.hhplus.be.server.api.queue.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Entity
@Table(name = "tb_queue")
public class Queue {
    @Id
    @Column(name = "queue_id", nullable = false)
    private Long id;

    @NotNull
    private Long userId;

    @Size(max = 255)
    @NotNull
    @Column(name = "token", nullable = false)
    private String token;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Lob
    @Column(name = "status", nullable = false)
    private QueueStatus status;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "entered_dt", nullable = false)
    private Instant enteredDt;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "expired_dt", nullable = false)
    private Instant expiredDt;

    protected Queue() {}

    public Queue(Long userId, String token, QueueStatus status, Instant enteredDt, Instant expiredDt) {
        this.userId = userId;
        this.token = token;
        this.status = status;
        this.enteredDt = enteredDt;
        this.expiredDt = expiredDt;
    }
}
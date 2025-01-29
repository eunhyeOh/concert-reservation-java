package kr.hhplus.be.server.api.queue.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@NoArgsConstructor
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
    private LocalDateTime enteredDt;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "expired_dt", nullable = false)
    private LocalDateTime expiredDt;


    public Queue(Long userId, String token, QueueStatus status, LocalDateTime enteredDt, LocalDateTime expiredDt) {
        this.userId = userId;
        this.token = token;
        this.status = status;
        this.enteredDt = enteredDt;
        this.expiredDt = expiredDt;
    }

    public void expire(){
        this.status = QueueStatus.EXPIRED;
    }
}
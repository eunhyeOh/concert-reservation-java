package kr.hhplus.be.server.api.concert.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kr.hhplus.be.server.common.config.jpa.BaseEntity;
import lombok.Getter;

@Getter
@Entity
@Table(name = "tb_concert")
public class Concert extends BaseEntity {
    @Id
    @Column(name = "concert_id", nullable = false)
    private Long id;

    @Size(max = 250)
    @NotNull
    @Column(name = "title", nullable = false, length = 250)
    private String title;

    @Size(max = 255)
    @Column(name = "description")
    private String description;

}
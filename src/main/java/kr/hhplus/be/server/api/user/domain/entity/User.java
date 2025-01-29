package kr.hhplus.be.server.api.user.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kr.hhplus.be.server.common.config.jpa.BaseEntity;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Entity
@Table(name = "tb_user")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Size(max = 250)
    @NotNull
    @Column(name = "user_mail", nullable = false, length = 250)
    private String userMail;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "user_amount", nullable = false)
    private Integer userAmount;


    public void chargeBalance(int amount){
        if(amount <= 0){
            throw new IllegalArgumentException("충전 금액은 0원초과여야 합니다.");
        }

        this.userAmount += amount;
    }

    public void deposit(int amount){
        if(this.userAmount < amount){
            throw new IllegalArgumentException("잔액이 부족합니다.");
        }
        this.userAmount -= amount;
    }
}
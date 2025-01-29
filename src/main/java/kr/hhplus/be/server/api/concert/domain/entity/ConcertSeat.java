package kr.hhplus.be.server.api.concert.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import kr.hhplus.be.server.common.config.jpa.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "tb_concert_seat")
public class ConcertSeat extends BaseEntity {
    @Id
    @Column(name = "seat_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "schedule_id", nullable = false)
    private Long scheduleId;

    @NotNull
    @Column(name = "seat_number", nullable = false)
    private Integer seatNumber;

    @NotNull
    @Column(name = "price", nullable = false)
    private Integer price;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ConcertSeatStatus status;



    /**
     * 좌석을 임시로 예약
     * */
    public void reserveTemporarily(){
        if(this.status != ConcertSeatStatus.AVAILABLE){
            throw new IllegalStateException("이미 선택된 좌석입니다.");
        }

        this.status = ConcertSeatStatus.TEMP_RESERVED; //'임시예약'상태로 설정
    }

    /**
     * 임시 예약을 해제
     * */
    public void releaseReservation(){
        this.status = ConcertSeatStatus.AVAILABLE;
    }

    /**
     * 좌석 예약 확정 처리
     * */
    public void confirmReservation(){
        if (this.status != ConcertSeatStatus.TEMP_RESERVED){
            throw new IllegalArgumentException("예약 가능한 상태가 아닙니다.");
        }

        this.status = ConcertSeatStatus.RESERVED; //'예약완료'상태로 설정
    }
}
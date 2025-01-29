package kr.hhplus.be.server.api.reservation.domain.service;

import jakarta.transaction.Transactional;
import kr.hhplus.be.server.api.concert.domain.entity.ConcertSeat;
import kr.hhplus.be.server.api.concert.domain.repository.SeatJpaRepository;
import kr.hhplus.be.server.api.reservation.domain.entity.Reservation;
import kr.hhplus.be.server.api.reservation.domain.repository.ReservationJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReservationService {

    private final SeatJpaRepository seatJpaRepository;
    private final ReservationJpaRepository reservationJpaRepository;


    public Reservation getReservation (Long reservationId){
        return reservationJpaRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("예약정보를 찾을 수 없습니다."));
    }

    /**
     * 좌석 임시 예약
     * */
    @Transactional
    public void reserveSeat(Long userId, Long seatId) {
        //좌석 확인 및 상태 변경 로직
        ConcertSeat seat = seatJpaRepository.findById(seatId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 좌석 선택입니다."));


        List<Reservation> activeReservations = reservationJpaRepository.findBySeatIdAndReservedUntillDtAfter(seatId, LocalDateTime.now());
        if(!activeReservations.isEmpty()){
            throw new IllegalArgumentException("이미 선택된 좌석입니다.");
        }

        //예약 생성
        Reservation reservation = new Reservation(userId, seatId);
        reservationJpaRepository.save(reservation);

        //좌석 상태 변경 : 임시예약
        seat.reserveTemporarily();
        seatJpaRepository.save(seat);

    }


    /**
     * 좌석 예약 완료 처리
     * */
    @Transactional
    public void confirmReservation(Long reservationId) {
        Reservation reservation = reservationJpaRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 예약정보 입니다."));

        ConcertSeat seat = seatJpaRepository.findById(reservation.getSeatId())
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 좌석정보입니다"));

        seat.confirmReservation();
        seatJpaRepository.save(seat);
    }


    /**
     * 예약 상태 검증
     * */
    public void validateReservation(Long reservationId) {
        Reservation reservation =  reservationJpaRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 예약정보 입니다."));

        if(reservation.isExpired()){
            throw new IllegalArgumentException("이미 선점된 좌석입니다.");
        }
    }


    @Transactional
    public void releaseExpiredReservations() {
        List<Reservation> expiredReservations = reservationJpaRepository.findByReservedUntillDtBefore(LocalDateTime.now());

        for(Reservation reservation : expiredReservations){
            ConcertSeat seat = seatJpaRepository.findById(reservation.getSeatId())
                    .orElse(null);
            if(seat != null){
                seat.releaseReservation();
                seatJpaRepository.save(seat); //예약가능상태로 변경
                reservationJpaRepository.delete(reservation); //예약 삭제
            }
        }
    }




}

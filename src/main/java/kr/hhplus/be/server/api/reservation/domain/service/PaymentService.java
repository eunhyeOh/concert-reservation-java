package kr.hhplus.be.server.api.reservation.domain.service;

import kr.hhplus.be.server.api.concert.domain.entity.ConcertSeat;
import kr.hhplus.be.server.api.concert.domain.service.ConcertService;
import kr.hhplus.be.server.api.reservation.domain.entity.*;
import kr.hhplus.be.server.api.reservation.domain.repository.PaymentHistoryJpaRepository;
import kr.hhplus.be.server.api.reservation.domain.repository.PaymentJpaRepository;
import kr.hhplus.be.server.api.user.domain.service.UserService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PaymentService {

    private final ReservationService reservationService;
    private final ConcertService concertService;
    private final UserService userService;

    private final PaymentJpaRepository paymentJpaRepository;
    private final PaymentHistoryJpaRepository paymentHistoryJpaRepository;

    @Transactional
    public Long executePayment(Long userId, Long reservationId) {
        //좌석, 유저 정보 조회
        Reservation reservation = reservationService.getReservation(reservationId);
        ConcertSeat seat = concertService.getSeat(reservation.getSeatId());

        //결제 데이터 생성(PROGRESS)
        Payment payment = new Payment(userId, reservationId, seat.getPrice());
        paymentJpaRepository.save(payment);

        try {
            userService.withdrawBalance(userId, seat.getPrice()); //유저 금액 차감처리
            concertService.confirmSeat(seat); //좌석 상태 변경

            payment.complete();
            paymentJpaRepository.save(payment);

        } catch (Exception e){
            payment.fail();
            paymentJpaRepository.save(payment);
            throw new RuntimeException("결제 실패" + e.getMessage());

        } finally {
            PaymentHistory paymentHistory = new PaymentHistory(userId, payment.getId(), -seat.getPrice(), PaymentHistoryType.USE);
            paymentHistoryJpaRepository.save(paymentHistory);
        }

        return payment.getId();
    }
}

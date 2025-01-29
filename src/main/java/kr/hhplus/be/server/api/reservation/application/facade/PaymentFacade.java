package kr.hhplus.be.server.api.reservation.application.facade;

import kr.hhplus.be.server.api.queue.domain.service.QueueService;
import kr.hhplus.be.server.api.reservation.domain.dto.PaymentResult;
import kr.hhplus.be.server.api.reservation.domain.service.PaymentService;
import kr.hhplus.be.server.api.reservation.domain.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class PaymentFacade {

    private final PaymentService paymentService;
    private final ReservationService reservationService;
    private final QueueService queueService;

    @Transactional
    public PaymentResult processPayment(Long userId, Long reservationId, String token) {

        //예약 만료 검증
        reservationService.validateReservation(reservationId);

        //결제 처리
        Long paymentId = paymentService.executePayment(userId, reservationId);

        //큐 토큰 만료 처리
        queueService.expireToken(token);

        return new PaymentResult("결제가 완료되었습니다.", paymentId);

    }
}

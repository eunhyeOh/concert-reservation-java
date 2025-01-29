package kr.hhplus.be.server.api.user.domain.service;

import jakarta.transaction.Transactional;
import kr.hhplus.be.server.api.reservation.domain.entity.PaymentHistory;
import kr.hhplus.be.server.api.reservation.domain.entity.PaymentHistoryType;
import kr.hhplus.be.server.api.reservation.domain.repository.PaymentHistoryJpaRepository;
import kr.hhplus.be.server.api.user.domain.dto.UserResult;
import kr.hhplus.be.server.api.user.domain.entity.User;
import kr.hhplus.be.server.api.user.domain.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class UserService {

    private final UserJpaRepository userJpaRepository;
    private final PaymentHistoryJpaRepository paymentHistoryJpaRepository;

    /**
     * 유저 정보 조회
     * */
    public UserResult getUser(Long userId) {

        User user = userJpaRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("유저 정보를 찾을 수 없습니다."));

        return new UserResult(user.getId(), user.getUserMail(), user.getUserAmount());
    }

    /**
     * 잔액 충전
     * */
    @Transactional
    public UserResult chargeAmount(Long userId, int amount) {

        if(amount <= 0) {
            throw new IllegalArgumentException("충전 금액을 확인하세요.");
        }

        //유저 조회
        User user = userJpaRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("유저 정보를 찾을 수 없습니다."));

        //잔액 충전 및 저장
        user.chargeBalance(amount);
        userJpaRepository.save(user);

        //결제 히스토리 저장
        PaymentHistory paymentHistory = new PaymentHistory(
                userId,
                0L,
                amount,
                PaymentHistoryType.CHARGE
        );
        paymentHistoryJpaRepository.save(paymentHistory);

        return new UserResult(user.getId(), user.getUserMail(), user.getUserAmount());
    }

    /**
     * 잔액 사용
     * */
    @Transactional
    public void withdrawBalance(Long userId, int amount) {
        User user = userJpaRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("유저 정보를 찾을 수 없습니다."));

        user.deposit(amount);
        userJpaRepository.save(user);
    }
}

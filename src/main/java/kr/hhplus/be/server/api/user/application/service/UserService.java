package kr.hhplus.be.server.api.user.application.service;

import jakarta.transaction.Transactional;
import kr.hhplus.be.server.api.reservation.domain.entity.PaymentHistory;
import kr.hhplus.be.server.api.reservation.domain.entity.PaymentHistoryType;
import kr.hhplus.be.server.api.reservation.domain.repository.PaymentHistoryJpaRepository;
import kr.hhplus.be.server.api.user.domain.entity.User;
import kr.hhplus.be.server.api.user.domain.repository.UserJpaRepository;
import kr.hhplus.be.server.api.user.presentation.dto.UserRequestDto;
import kr.hhplus.be.server.api.user.presentation.dto.UserResponseDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserJpaRepository userJpaRepository;
    private final PaymentHistoryJpaRepository paymentHistoryJpaRepository;

    public UserService(UserJpaRepository userJpaRepository, PaymentHistoryJpaRepository paymentHistoryJpaRepository) {
        this.userJpaRepository = userJpaRepository;
        this.paymentHistoryJpaRepository = paymentHistoryJpaRepository;
    }

    /**
     * 유저 정보 조회
     * */
    public User getUserAmount(Long userId) {

        Optional<User> user = userJpaRepository.findById(userId);
        if(user.isEmpty()) {
            throw new IllegalArgumentException("유저 정보를 찾을 수 없습니다.");
        }

        return user.get();
    }

    /**
     * 잔액 충전
     * */
    @Transactional
    public User chargeAmount(Long userId, UserRequestDto userRequestDto) {

        if(userRequestDto.getUserAmount() <= 0) {
            throw new IllegalArgumentException("충전 금액을 확인하세요.");
        }

        //유저 조회
        User user = userJpaRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저 정보를 찾을 수 없습니다."));

        //잔액 충전 및 저장
        user.chargeBalance(userRequestDto.getUserAmount());
        userJpaRepository.save(user);

        //결제 히스토리 저장
        PaymentHistory paymentHistory = new PaymentHistory(
                userId,
                0L,
                userRequestDto.getUserAmount(),
                PaymentHistoryType.CHARGE
        );
        paymentHistoryJpaRepository.save(paymentHistory);

        return user;
    }
}

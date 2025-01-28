package kr.hhplus.be.server.api.user.domain.repository;

public interface UserWalletRepository {
    Money getBalanceByUserId(Long userId);

    Money charge(Long userId, Money amount);

    Money deduct(Long userId, Money paymentAmount);
}

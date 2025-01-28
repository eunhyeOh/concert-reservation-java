package kr.hhplus.be.server.api.user.infrastructure.repository;

import kr.hhplus.be.server.api.user.domain.repository.UserWalletRepository;
import kr.hhplus.be.server.api.user.infrastructure.entity.TbUserWallet;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public class UserWalletRepositoryImpl implements UserWalletRepository {

    private final UserWalletJpaRepository jpaRepository;
    public UserWalletRepositoryImpl(UserWalletJpaRepository userWalletJpaRepository) {
        this.jpaRepository = userWalletJpaRepository;
    }

    @Override
    public Money getBalanceByUserId(Long userId){
        Optional<TbUserWallet> userWallet = jpaRepository.findById(userId);

        if (userWallet.isEmpty()) {
            throw new IllegalArgumentException("User ID에 해당하는 Wallet을 찾을 수 없습니다.");
        }

        return Money.of(userWallet.get().getCurrentBalance());
    }

    @Override
    public Money charge(Long userId, Money amount) {
        TbUserWallet wallet = jpaRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User ID에 해당하는 Wallet을 찾을 수 없습니다."));

        BigDecimal newBalance = wallet.getCurrentBalance().add(amount.getValue());
        wallet.setCurrentBalance(newBalance);
        wallet.setUpdatedAt(java.time.Instant.now());

        jpaRepository.save(wallet);

        return Money.of(newBalance);

    }

    @Override
    public Money deduct(Long userId, Money amount) {
        TbUserWallet wallet = jpaRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("User ID에 해당하는 Wallet을 찾을 수 없습니다."));

        BigDecimal remainingBalance = wallet.getCurrentBalance().subtract(amount.getValue());
        if(remainingBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw  new IllegalArgumentException("잔액이 부족합니다.");
        }

        wallet.setCurrentBalance(remainingBalance);
        wallet.setUpdatedAt(java.time.Instant.now());
        jpaRepository.save(wallet);

        return Money.of(remainingBalance);
    }
}

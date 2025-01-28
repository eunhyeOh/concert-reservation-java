package kr.hhplus.be.server.api.user.infrastructure.repository;

import kr.hhplus.be.server.api.user.infrastructure.entity.TbUserWallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserWalletJpaRepository extends JpaRepository<TbUserWallet, Long> {
    boolean existsByUserId(Long userId); // 사용자 ID 존재 여부 확인

    Optional<TbUserWallet> findByUserId(Long userId);
}

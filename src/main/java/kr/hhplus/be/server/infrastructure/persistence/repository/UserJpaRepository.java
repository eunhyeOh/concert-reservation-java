package kr.hhplus.be.server.infrastructure.persistence.repository;

import kr.hhplus.be.server.infrastructure.persistence.entity.TbUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<TbUser, Long> {

    Optional<TbUser> findByEmail(String email); // 이메일로 사용자 조회

    boolean existsByUserId(Long userId); // 사용자 ID 존재 여부 확인

}

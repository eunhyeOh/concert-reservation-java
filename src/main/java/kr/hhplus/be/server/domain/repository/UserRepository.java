package kr.hhplus.be.server.domain.repository;

import kr.hhplus.be.server.infrastructure.persistence.entity.TbUser;

import java.util.Optional;

//repository 인터페이스
public interface UserRepository {

    Optional<TbUser> findById(Long userId); // 사용자 ID로 조회
    Optional<TbUser> findByEmail(String email); // 이메일로 사용자 조회
    boolean existsById(Long userId); // 사용자 ID 존재 여부 확인
    void save(TbUser user); // 사용자 저장

}

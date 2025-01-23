package kr.hhplus.be.server.infrastructure.persistence.repository;

import kr.hhplus.be.server.domain.repository.UserRepository;
import kr.hhplus.be.server.infrastructure.persistence.entity.TbUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {


    private final UserJpaRepository userJpaRepository;

    @Autowired
    public UserRepositoryImpl(UserJpaRepository userJpaRepository) {

        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public Optional<TbUser> findById(Long userId) {
        return userJpaRepository.findById(userId);
    }

    @Override
    public Optional<TbUser> findByEmail(String email) {
        return userJpaRepository.findByEmail(email);
    }

    @Override
    public boolean existsById(Long userId) {
        return userJpaRepository.existsById(userId);
    }

    @Override
    public void save(TbUser user) {
        userJpaRepository.save(user);
    }
}

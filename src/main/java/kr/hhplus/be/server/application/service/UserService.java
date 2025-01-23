package kr.hhplus.be.server.application.service;

import kr.hhplus.be.server.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean validateUser(Long userId) {
        return userRepository.existsById(userId);
    }

}

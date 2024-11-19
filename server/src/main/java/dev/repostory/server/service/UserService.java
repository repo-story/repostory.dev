package dev.repostory.server.service;

import dev.repostory.server.entity.User;
import dev.repostory.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * 회원가입을 위해 중복 검증
     * @param slug
     * 회원가입 시 입력한 slug
     * @return Boolean
     */
    public Boolean isExistingSlug(String slug) {
        return userRepository.findBySlug(slug).isPresent();
    }

    public void joinUser(User user) {
        userRepository.save(user);
    }
}

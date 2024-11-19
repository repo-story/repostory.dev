package dev.repostory.server.repository;

import dev.repostory.server.entity.UserMailCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserMailCodeRepository extends JpaRepository<UserMailCode, String> {
    Optional<UserMailCode> findByEmail(String email);
}

package dev.repostory.server.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "user_mail_code")
@NoArgsConstructor
@RequiredArgsConstructor
public class UserMailCode {

    public static final int EXPIRED_MINUTES = 60;

    @Id
    @Size(max = 255)
    @NotNull
    @NonNull
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "id", nullable = false, length = 16, unique = true)
    private UUID id;

    @Column(name = "expired_at", nullable = false)
    private Instant expiredAt;

    @PrePersist
    @PreUpdate
    public void fillFields() {
        if (id == null) {
            id = UUID.randomUUID();
        }
        if (expiredAt == null) {
            expiredAt = Instant.now().plus(Duration.ofMinutes(EXPIRED_MINUTES));
        }
    }
}
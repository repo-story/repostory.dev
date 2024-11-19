package dev.repostory.server.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_admin", nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean isAdmin;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "email_hash", nullable = false, length = 64)
    private String emailHash;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 100)
    private String slug;

    @Column(name = "profile_img", length = 255)
    private String profileImg;

    @Column(length = 500)
    private String bio;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    @Column(name = "last_login_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp lastLoginAt;

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;
}

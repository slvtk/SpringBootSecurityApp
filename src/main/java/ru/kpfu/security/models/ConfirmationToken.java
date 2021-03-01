package ru.kpfu.security.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "confirmation_token")
public class ConfirmationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;
    private LocalDateTime activatedAt;
    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "student_id"
    )
    private Student student;

    public ConfirmationToken(String token,
                             LocalDateTime createdAt,
                             LocalDateTime expiredAt,
                             Student student) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
        this.student = student;
    }
}

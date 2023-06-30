package com.marzhiievskyi.home_notes.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(schema = "home_notes", name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;

    private String password;

    @Column(name = "access_token")
    private String accessToken;

    @Column(name = "time_insert")
    @CreationTimestamp
    private LocalDateTime timeInsert;
}

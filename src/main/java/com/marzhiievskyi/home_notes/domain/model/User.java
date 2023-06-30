package com.marzhiievskyi.home_notes.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
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
    private Date insertTime;
}

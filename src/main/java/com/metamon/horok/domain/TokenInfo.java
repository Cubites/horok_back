package com.metamon.horok.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Table(name = "token_info")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TokenInfo {

    /* DB에서 임시로 사용하기 위해 만들었다. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_info_id")
    private Integer id;

    private String accessToken;
    private String refreshToken;

    private String provider;
    private String email;

    public void saveTokenValue(String accessToken) {
        this.accessToken = accessToken;
    }

}
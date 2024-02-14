package com.metamon.horok.config.secs.oauth.horokjwt;

import lombok.Data;

@Data
public class GeneratedToken {
    private String accessToken;
    private String refreshToken;

    public GeneratedToken(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}

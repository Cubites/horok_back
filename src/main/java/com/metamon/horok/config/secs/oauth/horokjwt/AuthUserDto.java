package com.metamon.horok.config.secs.oauth.horokjwt;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class AuthUserDto {
    private Integer userId;
    private String email;
    private String nickname;
}

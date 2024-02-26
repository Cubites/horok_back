package com.metamon.horok.domain;




import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@RedisHash(value = "jwtToken",timeToLive = 60 * 60 * 24 * 14)
@ToString
public class TokenInfoRedis implements Serializable {
    @Id
    private String id;

    @Indexed
    private String accessToken;

    private String refreshToken;
    @Indexed
    private String provider;
    @Indexed
    private String email;

    public void updateAccessToken(String token){
        this.accessToken = token;
    }

}

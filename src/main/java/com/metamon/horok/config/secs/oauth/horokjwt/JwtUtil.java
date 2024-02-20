package com.metamon.horok.config.secs.oauth.horokjwt;

import com.metamon.horok.domain.TokenInfo;
import com.metamon.horok.repository.TokenInfoRepository;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
@Service
@RequiredArgsConstructor
@Slf4j
public class JwtUtil {

    private final TokenInfoRepository tokenInfoRepository;
    private final RefreshTokenService tokenService;
    private SecretKey secretKey;
    @Value("${spring.jwt.secret}")
    private String sign;
    @PostConstruct
    protected void init(){


        secretKey = new SecretKeySpec(sign.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    public GeneratedToken generatedToken(String email, String role,String provider){
        // refreshToken And accessToken 쌍으로 생성
        String refreshToken = generteRefreshToken(email,role);
        String accessToken = generateAccessToken(email, role);

        //token을 Mysql에 저장
        tokenSave(refreshToken,accessToken,email,provider);

        return new GeneratedToken(accessToken,refreshToken);

    }
    public GeneratedToken generatedTokenWithUserId(String email, String role,String provider,Integer userId){
        // refreshToken And accessToken 쌍으로 생성
        String refreshToken = generateRefreshTokenWithId(email,role,userId);
        String accessToken = generateAccessTokenWithId(email,role,userId);

        //token을 Mysql에 저장 리프레쉬 토큰 때문임
        tokenSave(refreshToken,accessToken,email,provider);

        return new GeneratedToken(accessToken,refreshToken);

    }

    private void tokenSave(String refreshToken, String accessToken, String email,String provider) {
        TokenInfo tokenInfo = TokenInfo.builder()
                .refreshToken(refreshToken)
                .accessToken(accessToken)
                .provider(provider)
                .email(email)
                .build();
        tokenService.saveNewLoginToken(tokenInfo);
//        tokenInfoRepository.save(tokenInfo);
    }

    public String generteRefreshToken(String email, String role){
        //토큰의 유효 기간을 설정
        long refreshExpiration = 1000L * 60L * 60L * 24L * 7; // 1주

        return Jwts.builder()
                .claim("email",email)
                .claim("role",role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+refreshExpiration))
                .signWith(secretKey)
                .compact();
    }

    public String generateAccessToken(String email,String role){
        long tokenPeriod = 1000L * 60L * 30L; //30분
        //long tokenPeriod = 1000L * 60L * 1L; //test용 1분

        return Jwts.builder()
                .claim("email",email)
                .claim("role",role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+tokenPeriod))
                .signWith(secretKey)
                .compact();
    }


    public String generateRefreshTokenWithId (String email, String role,Integer userId){
        //토큰의 유효 기간을 설정
        long refreshExpiration = 1000L * 60L * 60L * 24L * 7; // 1주
        //ong refreshExpiration  = 1000L * 60L * 1L; //test용 1분

        return Jwts.builder()
                .claim("email",email)
                .claim("role",role)
                .claim("userId",userId)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+refreshExpiration))
                .signWith(secretKey)
                .compact();
    }

    public String generateAccessTokenWithId (String email,String role, Integer userId){
        long tokenPeriod = 1000L * 60L * 30L; //30분
        // long tokenPeriod = 1000L * 60L * 1L; //test용 1분

        return Jwts.builder()
                .claim("email",email)
                .claim("role",role)
                .claim("userId",userId)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+tokenPeriod))
                .signWith(secretKey)
                .compact();
    }




    public boolean isNotExpired(String token) throws JwtException {
        // 검증시 발생할 수 있는 예외는 필터에서 잡을 것 따라서 여기선
        // 단순히 토큰이 유효한지만 검사
        // 예외 함 봐보자

        boolean before = Jwts.parser().verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration()
                .before(new Date());

        return !before;

    }

    public String getEmail(String token){
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("email",String.class);
    }

    public String getRole(String token){
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("role",String.class);
    }

    public Integer getUserId(String token){
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("userId",Integer.class);
    }
}

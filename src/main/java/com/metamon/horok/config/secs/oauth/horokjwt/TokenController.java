package com.metamon.horok.config.secs.oauth.horokjwt;

import com.metamon.horok.config.secs.oauth.oauthdao.StatusResponseDto;
import com.metamon.horok.domain.TokenInfo;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TokenController {

    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;


    @PostMapping("/token/logout")
    public ResponseEntity<StatusResponseDto> logout(@RequestHeader("Authorization") final String accessToken) {

        // 엑세스 토큰으로 현재 Redis 정보 삭제 / 지금은 DB에서 지우자
        refreshTokenService.deleteToken(accessToken);

        return ResponseEntity.ok(StatusResponseDto.builder().status(200).build());
    }

    @PostMapping("/token/refresh")
    public ResponseEntity<StatusResponseDto> refresh(@RequestHeader("Authorization") final String accessToken) throws Exception {

        // 액세스 토큰으로 Refresh 토큰 객체를 조회
        TokenInfo tokenInfo = refreshTokenService.findRefreshToken(accessToken);


        String refreshToken = tokenInfo.getRefreshToken();
        String acToken = tokenInfo.getAccessToken();
        // RefreshToken이 존재하고 유효하다면 실행
        if(StringUtils.isNotEmpty(refreshToken) && jwtUtil.isNotExpired(refreshToken)) {
            // RefreshToken 객체를 꺼내온다.
            String email = jwtUtil.getEmail(refreshToken);
            String role = jwtUtil.getRole(refreshToken);
            // 권한과 아이디를 추출해 새로운 액세스토큰을 만든다.
            String newAccessToken = jwtUtil.generateAccessToken(email, role);
            // 액세스 토큰의 값을 수정해준다.
            refreshTokenService.saveAccessToken(acToken,newAccessToken);


            // 새로운 액세스 토큰을 반환해준다.
            return ResponseEntity.ok(StatusResponseDto.builder().status(200).accessToken(newAccessToken).build());
        }

        //refreshToken이 유효하지않을때 -> 응답 받아서 sns첫 로그인으로 보낼 것
        return ResponseEntity.badRequest().body(StatusResponseDto.builder().status(401).tokenExpired(true).build());

        }



    }



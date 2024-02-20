package com.metamon.horok.config.secs.oauth.horokjwt;

import com.metamon.horok.config.secs.oauth.CookieUtils;
import com.metamon.horok.config.secs.oauth.oauthdao.StatusResponseDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
public class TokenController {

    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;




    @PostMapping("/token/logout")
    public ResponseEntity<StatusResponseDto> logout2(@CookieValue("Authorization") String accessToken, HttpServletRequest request, HttpServletResponse response) {

        // 엑세스 토큰으로 현재 Redis 정보 삭제 / 지금은 DB에서 지우자
        refreshTokenService.deleteToken(accessToken);
        CookieUtils.deleteCookie(request,response,"Authorization");
        return ResponseEntity.ok(StatusResponseDto.builder().status(200).build());
    }



    }



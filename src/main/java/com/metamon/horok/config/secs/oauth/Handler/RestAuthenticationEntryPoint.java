package com.metamon.horok.config.secs.oauth.Handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.metamon.horok.config.secs.oauth.oauthdao.StatusResponseDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {


        log.info("여기는 필터입니다 로그인시 통과하나요 여기는 노 로그인된거에요 이리오면안대요");

        StatusResponseDto build = StatusResponseDto
                .builder().status(401).msg("로그인이 필요한 페이지 입니다.").build();

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpStatus.UNAUTHORIZED.value()); //401 에러 보냄
            response.setCharacterEncoding("UTF-8");


        objectMapper.writeValue(response.getWriter(), build);



    }
}

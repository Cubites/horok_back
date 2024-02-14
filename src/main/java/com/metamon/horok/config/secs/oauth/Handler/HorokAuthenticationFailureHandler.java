package com.metamon.horok.config.secs.oauth.Handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Slf4j
@Component

//OAuth2 회원 인증에 실패한 경우 (회원가입 유무와 상관 없음)
public class HorokAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
       log.info("인증실패");
        response.sendRedirect("/test");
        response.sendRedirect("http://localhost:8080/");

    }
}

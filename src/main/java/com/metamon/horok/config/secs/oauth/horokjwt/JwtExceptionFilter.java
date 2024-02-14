package com.metamon.horok.config.secs.oauth.horokjwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.metamon.horok.config.secs.oauth.oauthdao.StatusResponseDto;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtExceptionFilter extends OncePerRequestFilter {

   private final ObjectMapper objectMapper;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request,response);
        }catch (AccessTokenExpiredException e){
            response.setStatus(401);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            objectMapper.writeValue(response.getWriter(), StatusResponseDto.builder().status(401).tokenExpired(true).build());
        }catch (JwtException e){
            StatusResponseDto build = StatusResponseDto.builder().status(401).msg("Access 토큰이 조작 혹은 해당 애플리케이션에서 발행한 토큰이 아닙니다.").build();
            response.setStatus(401);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            objectMapper.writeValue(response.getWriter(),build);
        }
    }
}

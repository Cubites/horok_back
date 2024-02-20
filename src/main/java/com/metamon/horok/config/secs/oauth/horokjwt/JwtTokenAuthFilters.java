package com.metamon.horok.config.secs.oauth.horokjwt;

import com.metamon.horok.config.secs.oauth.CookieUtils;
import com.metamon.horok.domain.TokenInfo;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Component
public class JwtTokenAuthFilters extends OncePerRequestFilter {
    private final String REFRESH_EXPIRED="RefreshExpired";
    private final JwtUtil jwtUtil;


    private final RefreshTokenService refreshTokenService;


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        return request.getRequestURI().contains("/token/refresh") || request.getRequestURI().contains("/login/page");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authroization = null;
        Cookie[] cookies = request.getCookies();


        if(cookies != null) {
            for (Cookie cookie : cookies) {

                if (cookie.getName().equals("Authorization")) {
                    authroization = cookie.getValue();
                }
            }
        }


        if(authroization == null){

            filterChain.doFilter(request,response);
            return;
        }
        String acctoken = authroization;

        try {

            jwtUtil.isNotExpired(acctoken);

        }catch (ExpiredJwtException es){

            if((acctoken = tokenRefresh(request,response)).equals(REFRESH_EXPIRED))
                throw new RefreshTokenExpiredException("Refresh Token 만료");
            else {

            }

            //토큰이 expired 된 것 처리
        }
        catch (JwtException e) {

            //토큰 조작 처리

            //나머지 Eecption

            throw new RuntimeException(e);
        }

        //accessToken의 값이 유효함



        Integer userId = jwtUtil.getUserId(acctoken);
        String email = jwtUtil.getEmail(acctoken);
        String role = jwtUtil.getRole(acctoken);
        AuthUserDto authUserDto=  AuthUserDto.builder()
                .userId(userId)
                .email(email)
                .role(role)
                .build();


        //SecurityContext에 인증 객체를 등록 (1회용) -> 세션이 Stateless라그럼
        Authentication auth = new UsernamePasswordAuthenticationToken(authUserDto,"", List.of(new SimpleGrantedAuthority("ROLE_USER")));
        SecurityContextHolder.getContext().setAuthentication(auth);




        filterChain.doFilter(request, response);
    }

    private String tokenRefresh(HttpServletRequest request, HttpServletResponse response) {

        // 액세스 토큰으로 Refresh 토큰 객체를 조회

        String expiredToken = CookieUtils.getCookie(request, "Authorization").get().getValue();
        TokenInfo tokenInfo = refreshTokenService.findRefreshToken(expiredToken);
        String refreshToken = tokenInfo.getRefreshToken();
        try {


            if (StringUtils.isNotEmpty(refreshToken) && jwtUtil.isNotExpired(refreshToken)) {
                // RefreshToken 객체를 꺼내온다.
                String email = jwtUtil.getEmail(refreshToken);
                String role = jwtUtil.getRole(refreshToken);
                Integer userId = jwtUtil.getUserId(refreshToken);


                String newAccessToken = jwtUtil.generateAccessTokenWithId(email, role, userId);
                // 액세스 토큰의 값을 수정해준다.
                refreshTokenService.updateAccessToken(expiredToken, newAccessToken);

                CookieUtils.deleteCookie(request, response, "Authorization");
                CookieUtils.addCookie(response,"Authorization", newAccessToken, 1000 * 60 * 40);



                return newAccessToken;
            }
        }catch (JwtException e){

        }
        return REFRESH_EXPIRED;
    }
}

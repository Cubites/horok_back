package com.metamon.horok.config.secs.oauth.horokjwt;

import com.metamon.horok.repository.UsersRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Component
public class JwtTokenAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    private final UsersRepository usersRepository;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        return request.getRequestURI().contains("/token/refresh");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       //request Header에서 Access Token을 가져온다.

        String acctoken = request.getHeader("Authorization");

        //모두 허용 URL일 경우 토큰 검사 생략
        if(!StringUtils.hasText(acctoken)){
            doFilter(request,response,filterChain);
            return;
        }
        //AccessToken을 검증, 만료되었을 경우 예외를 발생시킨다.(리프레쉬 토큰 확인해서 재발급)
        try {

            jwtUtil.isNotExpired(acctoken);

        }catch (ExpiredJwtException es){
            //토큰이 expired 된 것 처리

            throw new AccessTokenExpiredException("Access Token 만료");
        }
        catch (JwtException e) {
            //토큰 조작 처리

            //나머지 Eecption

            throw new RuntimeException(e);
        }

        //accessToken의 값이 유효함


            //email 혹은 특정 값으로 멤버 조회 userId
            //Users byUserNickname = usersRepository.findByUserNickname("강태바리")

        Integer userId = Integer.valueOf(jwtUtil.getUserId(acctoken));
        String email = jwtUtil.getEmail(acctoken);

        AuthUserDto authUserDto=  AuthUserDto.builder()
                    .userId(userId).email(email).build();


            //SecurityContext에 인증 객체를 등록 (1회용) -> 세션이 Stateless라그럼
            Authentication auth = new UsernamePasswordAuthenticationToken(authUserDto,"", List.of(new SimpleGrantedAuthority("ROLE_USER")));
            SecurityContextHolder.getContext().setAuthentication(auth);


            //authUserDto가 principal로 셋팅된다.
            //authUserDto를 얻기 위해서는
            // SecurityContextHolder.getContext().~~->..getPrincipal()이다
            // 걍 어노테이션으로 처리하자

        filterChain.doFilter(request, response);
    }
}

package com.metamon.horok.config.secs.oauth.Handler;

import com.metamon.horok.config.secs.oauth.horokjwt.GeneratedToken;
import com.metamon.horok.config.secs.oauth.horokjwt.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
@Slf4j
@RequiredArgsConstructor
public class HorokSuccessHandler implements AuthenticationSuccessHandler {
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private final JwtUtil jwtUtil;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("여기 온것이여");
        // OAuth2User로 캐스팅하여 인증된 사용자 정보를 가져온다.
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        // 사용자 이메일을 가져온다.
        String email = oAuth2User.getAttribute("email");
        // 서비스 제공 플랫폼(GOOGLE, KAKAO, NAVER)이 어디인지 가져온다.
        String provider = oAuth2User.getAttribute("provider");

        // CustomOAuth2UserService에서 셋팅한 로그인한 회원 존재 여부를 가져온다.
        boolean isExist = oAuth2User.getAttribute("exist");
        log.info("isExist = {}",isExist);
        // OAuth2User로 부터 Role을 얻어온다.
        String role = oAuth2User.getAuthorities().stream().
                findFirst() // 첫번째 Role을 찾아온다.
                .orElseThrow(IllegalAccessError::new) // 존재하지 않을 시 예외를 던진다.
                .getAuthority(); // Role을 가져온다.
        log.info("여기까지 오냐?");
        // 회원이 존재할경우
        if (isExist) {
            // 회원이 존재하면 jwt token 발행을 시작한다.
            GeneratedToken token = jwtUtil.generatedToken(email,role);
            log.info("jwtToken = {}", token.getAccessToken());

            // accessToken을 쿼리스트링에 담는 url을 만들어준다.
            String targetUrl = UriComponentsBuilder.fromUriString("/loginSuccess")
                    .queryParam("accessToken", token.getAccessToken())
                    .build()
                    .encode(StandardCharsets.UTF_8)
                    .toUriString();
            log.info("redirect 준비");
            //로그인 확인 페이지로 리다이렉트 시킨다.
          //  getRedirectStrategy().sendRedirect(request, response, targetUrl);
            redirectStrategy.sendRedirect(request,response,targetUrl);

        } else {

            log.info("여기 준비중");
            String requestURI = request.getRequestURI();
            log.info("requestURI {}",requestURI);
            // 회원이 존재하지 않을경우, 서비스 제공자와 email을 쿼리스트링으로 전달하는 url을 만들어준다.
            String targetUrl = UriComponentsBuilder.fromUriString("http://localhost:8080/loginSuccess")
                    .queryParam("email", (String) oAuth2User.getAttribute("email"))
                    .queryParam("provider", provider)
                    .build()
                    .encode(StandardCharsets.UTF_8)
                    .toUriString();
            // 회원가입 페이지로 리다이렉트 시킨다.
          //  getRedirectStrategy().sendRedirect(request, response, targetUrl);
            redirectStrategy.sendRedirect(request,response,targetUrl);
        }

        //어느쪽이던 loginSuccess로 보낸다. 리액트에서 이를 받아서 처리하도록함 -> 기존 유저면 해당 토큰을 사용하도록 하고,
        // 신규유저면, 토큰이 아닌 가입하도록 유도
        // 로그인에 성공한 유저는 토큰을 받아서 다음 요청시 헤더에 포함해서 요청을 보낸다.
        // 아직 회원가입이 안된 유저는 회원가입 페이지로 보내서 회원가입 시키고, 다시 요청 보내도록한다. 이때 이메일과 제공자를 쿼리스트링에 담아서 보내면,
        // 후론트에서 값을 꺼내서 미리 저장하고 있다가 DB에 입력
    }
}

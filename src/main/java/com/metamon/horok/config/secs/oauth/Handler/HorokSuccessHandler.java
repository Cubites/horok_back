package com.metamon.horok.config.secs.oauth.Handler;

import com.metamon.horok.config.secs.oauth.CookieUtils;
import com.metamon.horok.config.secs.oauth.HttpCookieOAuth2AuthorizationRequestRepository;
import com.metamon.horok.config.secs.oauth.horokjwt.GeneratedToken;
import com.metamon.horok.config.secs.oauth.horokjwt.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static com.metamon.horok.config.secs.oauth.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@Component
@Slf4j
@RequiredArgsConstructor
public class HorokSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtUtil jwtUtil;
    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        // OAuth2User로 캐스팅하여 인증된 사용자 정보를 가져온다.
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        // 사용자 이메일을 가져온다.
        String email = oAuth2User.getAttribute("email");
        // 서비스 제공 플랫폼(GOOGLE, KAKAO, NAVER)이 어디인지 가져온다.
        String provider = oAuth2User.getAttribute("provider");

        // CustomOAuth2UserService에서 셋팅한 로그인한 회원 존재 여부를 가져온다.
        boolean isExist = oAuth2User.getAttribute("exist");

        // OAuth2User로 부터 Role을 얻어온다.
        String role = oAuth2User.getAuthorities().stream().
                findFirst() // 첫번째 Role을 찾아온다.
                .orElseThrow(IllegalAccessError::new) // 존재하지 않을 시 예외를 던진다.
                .getAuthority(); // Role을 가져온다.


        String redirectUri = determineTargetUrl(request, response, authentication);

        //첫 요청 정보 들어왔던 쿠키 비워주기
        clearAuthenticationAttributes(request,response);
        // 회원이 존재할경우
        if (isExist) {

            // 회원이 존재하면 jwt token 발행을 시작한다.
            // 토큰에 넣을 userId값

            Integer userId = oAuth2User.getAttribute("userId");
            GeneratedToken token = jwtUtil.generatedTokenWithUserId(email,role,provider,userId);



            CookieUtils.addCookie(response,"Authorization", token.getAccessToken(), 1000 * 60 * 40);
            //response.addCookie(createCookie("Authorization", token.getAccessToken()));
            response.sendRedirect(redirectUri);


        } else {


            String requestURI = request.getRequestURI();

            // 회원이 존재하지 않을경우, 서비스 제공자와 email을 쿼리스트링으로 전달하는 url을 만들어준다.
            String targetUrl = UriComponentsBuilder.fromUriString(redirectUri)
                    .queryParam("email", (String) oAuth2User.getAttribute("email"))
                    .queryParam("provider", provider)
                    .build()
                    .encode(StandardCharsets.UTF_8)
                    .toUriString();
            // 회원가입 페이지로 리다이렉트 시킨다.
            getRedirectStrategy().sendRedirect(request, response, targetUrl);

        }

    }

    private Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        //cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Optional<String> redirectUri = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);

        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());
        return targetUrl;
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }
}
package com.metamon.horok.config.secs.oauth.Handler;

import com.metamon.horok.config.secs.oauth.horokjwt.GeneratedToken;
import com.metamon.horok.config.secs.oauth.horokjwt.JwtUtil;
import com.metamon.horok.domain.UserLoginInfo;
import com.metamon.horok.domain.Users;
import com.metamon.horok.repository.UserLoginInfoRepository;
import com.metamon.horok.repository.UsersRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class TestHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtUtil jwtUtil;
    private final UsersRepository usersRepository;
    private final UserLoginInfoRepository userLoginInfoRepository;
    private static final String REDIRECT_URL = "http://localhost:8081/loginSuccess";
    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //자신한테 보내면
        //getRedirectStrategy().sendRedirect(request,response,REDIRECT_URL);\
        //내 컴퓨터로는 절대 못보내고, 다른 컴퓨터로는 보낼 수 있음
        //response.sendRedirect("/loginSuccess");

        //따라서 redirect 보내지말고, 그냥 결과 화면만 보고 저장된 걸로 접근해보아야 겠다.

        //test 1
        //존재하지 않는 회원이면, email, provider 정보를 여기서 불러와서 회원가입 하도록 유도해야함

        //공통로직

        // OAuth2User로 캐스팅하여 인증된 사용자 정보를 가져온다. (로그인시 Oauth2Service를 통해 검증받은 부분)
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        log.info("attribute {}",oAuth2User.getAttributes());
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

        if(isExist){
            UserLoginInfo userLoginInfo = userLoginInfoRepository.findByUserLoginEmail(email).get();
            userLoginInfo.getUser().getUserId();
            // 회원이 존재하면 jwt token 발행을 시작한다.
            GeneratedToken token = jwtUtil.generatedToken(email,role);
            log.info("jwtToken = {}", token.getAccessToken());

            //원래는 헤더에 담아서 보내줘야하는데, 그냥 보여주자
            response.setContentType("text/html;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            PrintWriter writer = response.getWriter();
            writer.write(token.toString());
        }else{
           //유저정보 생성해서 저장
            UserLoginInfo userLoginInfo = UserLoginInfo
                    .builder().userLoginEmail(email)
                            .userLoginRole(role)
                                            .build();
            Users users = Users.builder().userNickname("강태바리").userLoginInfo(userLoginInfo)
                    .build();

            userLoginInfo.setUser(users);

            usersRepository.save(users);

            response.setContentType("text/html;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            PrintWriter writer = response.getWriter();
            writer.write("user created");
        }
    }
}

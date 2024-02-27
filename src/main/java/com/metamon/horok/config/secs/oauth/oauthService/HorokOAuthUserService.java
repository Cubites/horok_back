package com.metamon.horok.config.secs.oauth.oauthService;

import com.metamon.horok.config.secs.oauth.oauthdao.Oauth2Attribute;

import com.metamon.horok.repository.UserLoginInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;

import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class HorokOAuthUserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserLoginInfoRepository userLoginInfoRepository;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {



        //기본 OAuth2USerService 생성
        OAuth2UserService<OAuth2UserRequest,OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();
        //OAuth2UserSerivce를 사용하여 OAuth2User 정보 가져옴 //userRequest가 외부 인증서버에서 발급받은 토큰개념임
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest); // oauth2에서 받은 access 토큰에서 유저정보 꺼낸 것

        //Naver,kakao같은 정보
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        //사용자명
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        //기본 userService에서 가져온 유저 정보를 기반으로 OAuth2Attribute 객체를 만든다
        Oauth2Attribute oauth2Attribute = Oauth2Attribute.of(registrationId,userNameAttributeName,oAuth2User.getAttributes());


        //파싱한 값들 중 필요 정보를 담은 Map임
        Map<String, Object> memberAttribute = oauth2Attribute.convertToMap();

        String email = (String) memberAttribute.get("email");
        String provider = (String)memberAttribute.get("provider");

        //등록된 이메일을 기준으로 우리 어플리케이션에 저장된 회원인지 아닌지 판단
        List<Object[]> userAndEmail = userLoginInfoRepository.findUserAndEmail(email,provider);

        if (userAndEmail.size() == 0) {
            // 회원이 존재하지 않을경우, memberAttribute의 exist 값을 false로 넣어준다.
            memberAttribute.put("exist", false);
            return new DefaultOAuth2User(
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                    memberAttribute, "email");
        }

        // 회원이 존재할경우, memberAttribute의 exist 값을 true로 넣어준다.
        // 쿠키를 발급하기 위한 userId값도 넣어준다.
        memberAttribute.put("exist", true);
        memberAttribute.put("userId",(Integer)userAndEmail.get(0)[0]);
        // 회원의 권한과, 회원속성, 속성이름을 이용해 DefaultOAuth2User 객체를 생성해 반환한다.
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_".concat((String)userAndEmail.get(0)[2]))),memberAttribute, "email");
    }
}

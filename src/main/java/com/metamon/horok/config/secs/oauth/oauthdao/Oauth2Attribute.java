package com.metamon.horok.config.secs.oauth.oauthdao;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;
@ToString
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class Oauth2Attribute {
    private Map<String,Object> attributes; // 사용자 속성 정보를 담는 Map
    private String attributeKey; // 사용자 속성의 키 값
    private String email;
    private String name;
    private String provider;

    public static Oauth2Attribute of(String provider,String attributeKey, Map<String,Object> attributes){
        switch (provider){
            case "google":
                return ofGoogle(provider, attributeKey, attributes);
            case "kakao":
                return ofKakao(provider,"id", attributes);
            case "naver":
                return ofNaver(provider, "email", attributes);
            default:
                throw new RuntimeException();
        }
    }

    private static Oauth2Attribute ofGoogle(String provider, String attributeKey,
                                            Map<String, Object> attributes) {
        return Oauth2Attribute.builder()
                .email((String) attributes.get("email"))
                .provider(provider)
                .attributes(attributes)
                .attributeKey(attributeKey)
                .build();
    }

    private static Oauth2Attribute ofKakao(String provider, String attributeKey,
                                           Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");

        return Oauth2Attribute.builder()
                .email((String) kakaoAccount.get("email"))
                .provider(provider)
                .attributes(kakaoAccount)
                .attributeKey(attributeKey)
                .build();
    }

    private static Oauth2Attribute ofNaver(String provider, String attributeKey,
                                           Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return Oauth2Attribute.builder()
                .email((String) response.get("email"))
                .name((String) response.get("name"))
                .attributes(response)
                .provider(provider)
                .attributeKey(attributeKey)
                .build();
    }


    // OAuth2User 객체에 넣어주기 위해서 Map으로 값들을 반환해준다.
    public Map<String, Object> convertToMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", attributeKey);
        map.put("key", attributeKey);
        map.put("email", email);
        map.put("provider", provider);
        map.put("name",name);

        return map;
    }
}

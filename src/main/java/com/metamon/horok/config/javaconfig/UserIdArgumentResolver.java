package com.metamon.horok.config.javaconfig;

import com.metamon.horok.config.secs.oauth.CookieUtils;
import com.metamon.horok.config.secs.oauth.horokjwt.AuthUserDto;
import com.metamon.horok.config.secs.oauth.horokjwt.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserIdArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private final JwtUtil jwtUtil;
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasAnnotation = parameter.hasParameterAnnotation(UserIdFromJwt.class);
        boolean hasIntegerType = Integer.class.isAssignableFrom(parameter.getParameterType());
        return hasAnnotation && hasIntegerType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {



        HttpServletRequest request = (HttpServletRequest)webRequest.getNativeRequest();


        // 쿠키 없을 때 처리 해야함


        Optional<Cookie> authorization = CookieUtils.getCookie(request, "Authorization");
        Cookie cookie = authorization.orElse(null);
        if(cookie != null){
            String value = cookie.getValue();
            System.out.println(" **********************쿠키 토큰 값" );
            System.out.println("value = " + value);
            System.out.println(" **********************쿠키 토큰 값" );
            Integer userId = jwtUtil.getUserId(value);
            return userId;
        }else{
            //어차피 미인증 사용자는 필터에서 막힌다.
            return 12345;
        }
    }
}

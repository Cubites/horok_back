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


//
//          추후에 여기서 값 꺼내서 줄 것
           HttpServletRequest request = (HttpServletRequest)webRequest.getNativeRequest();
//        Optional<Cookie> authorization = CookieUtils.getCookie(request, "Authorization");
//        Cookie cookie = authorization.orElse(null);
        // 지금은 그냥 user Id주자
        // 여기에 사용하려는 userId를 셋팅해주세요

       // String token = cookie.getValue();
        //Integer userId = jwtUtil.getUserId(token);

        // 쿠키 없을 때 처리 해야함
        System.out.println("************************************");
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                System.out.println("************************************");
                System.out.println("cookie = " + cookie.getName());
                System.out.println("cookie.getValue() = " + cookie.getValue());
                System.out.println("************************************");
            }
        }
        System.out.println("************************************");
        return 171;
    }
}

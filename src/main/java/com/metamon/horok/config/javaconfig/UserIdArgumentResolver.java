package com.metamon.horok.config.javaconfig;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
@Component
public class UserIdArgumentResolver implements HandlerMethodArgumentResolver {



    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasAnnotation = parameter.hasParameterAnnotation(UserIdFromJwt.class);
        boolean hasIntegerType = Integer.class.isAssignableFrom(parameter.getParameterType());
        return hasAnnotation && hasIntegerType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        //추후에 여기서 값 꺼내서 줄 것
        //AuthUserDto principal = (AuthUserDto)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //지금은 그냥 user Id주자
        //여기에 사용하려는 userId를 셋팅해주세요
        Integer userIdInteger = Integer.valueOf("171");
      
        return userIdInteger;
    }
}

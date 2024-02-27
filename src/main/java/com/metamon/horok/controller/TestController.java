package com.metamon.horok.controller;

import com.metamon.horok.config.javaconfig.UserIdFromJwt;
import com.metamon.horok.config.secs.oauth.horokjwt.AuthUserDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController {

    @GetMapping("/")
    public String maintest(HttpServletRequest request){

        return "test";

    }


    @GetMapping("/loginSuccess")
    public AuthUserDto success(Authentication authenticationPrincipal){

        AuthUserDto principal = (AuthUserDto)authenticationPrincipal.getPrincipal();
        System.out.println("principal = " + principal);
        return principal;
    }

    @GetMapping("/acTest")
    public String argumentResult(@UserIdFromJwt Integer userid){



        log.info("여기는작동하니");
        return userid+"";
    }
}

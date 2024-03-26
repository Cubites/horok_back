package com.metamon.horok.config.secs.oauth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseCookie;
import org.springframework.util.SerializationUtils;

import java.net.ResponseCache;
import java.util.Base64;
import java.util.Optional;

public class CookieUtils {

    public static Optional<Cookie> getCookie(HttpServletRequest request, String name){
        Cookie[] cookies = request.getCookies();
        if(cookies != null && cookies.length >0){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals(name)){

                    return Optional.of(cookie);
                }
            }
        }
        return Optional.empty();
    }

    public static Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        //cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {


        ResponseCookie none = ResponseCookie.from(name, value).path("/").maxAge(maxAge).sameSite("None")
                .secure(true).httpOnly(true).domain("horok.link").
                build();
        response.addHeader("Set-Cookie",none.toString());



    }
    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie: cookies) {
                if (cookie.getName().equals(name)) {

                    ResponseCookie responseCookie = ResponseCookie.from(name,"")
                            .path("/")
                            .maxAge(0)
                            .sameSite("None")
                            .secure(true)
                            .httpOnly(true)
                            .domain("horok.link")
                            .build();

                    response.addHeader("Set-Cookie",responseCookie.toString());
                }
            }
        }
    }
    public static String serialize(Object object) {
        return Base64.getUrlEncoder()
                .encodeToString(SerializationUtils.serialize(object));
    }
    public static <T> T deserialize(Cookie cookie, Class<T> cls) {
        return cls.cast(SerializationUtils.deserialize(
                Base64.getUrlDecoder().decode(cookie.getValue())));
    }
}

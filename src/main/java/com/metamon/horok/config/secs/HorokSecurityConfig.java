package com.metamon.horok.config.secs;

import com.metamon.horok.config.secs.oauth.Handler.HorokAuthenticationFailureHandler;
import com.metamon.horok.config.secs.oauth.Handler.HorokAuthenticationSuccessHandler;
import com.metamon.horok.config.secs.oauth.Handler.HorokSuccessHandler;
import com.metamon.horok.config.secs.oauth.Handler.TestHandler;
import com.metamon.horok.config.secs.oauth.horokjwt.JwtExceptionFilter;
import com.metamon.horok.config.secs.oauth.horokjwt.JwtTokenAuthFilter;
import com.metamon.horok.config.secs.oauth.oauthService.HorokOAuthUserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;


@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class HorokSecurityConfig {
    private final TestHandler testHandler;
    private final HorokSuccessHandler horokSuccessHandler;
    private final HorokOAuthUserService horokOAuthUserService;
    //OAuth2요청 실패시 작동
    private final HorokAuthenticationFailureHandler horokAuthenticationFailureHandler;
    //OAuth2요청 성공시 작동
    private final HorokAuthenticationSuccessHandler horokAuthenticationSuccessHandler;

   // private final TokenService tokenService;
    private final JwtExceptionFilter jwtExceptionFilter;
    private final JwtTokenAuthFilter jwtTokenAuthFilter;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        //csrf 일단 disable (api서버는 꺼도 된다함)
        http.csrf((auth) -> auth.disable())
                .formLogin((auth) -> auth.disable())
                        .httpBasic((auth)->auth.disable())
                                .sessionManagement((s)->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        //리액트 연동을 위한 cors설정
        http.cors(cors -> cors.configurationSource(new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration config = new CorsConfiguration();
                config.addAllowedOriginPattern("*");
                config.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
                config.setAllowedMethods(Collections.singletonList("*"));
                config.setAllowCredentials(true);
                config.setAllowedHeaders(Collections.singletonList("*"));
                return config;
            }
        }));

        http.authorizeHttpRequests((auth) ->
                //일단 모든 요청에 대해 시큐리티 작동안하게 막음 추후에 풀 것
                auth.requestMatchers("/","/oauth2/**","/login/**","/token/**").permitAll()
                        //.anyRequest().authenticated()
                        .anyRequest().permitAll()
        ).
                addFilterBefore(jwtTokenAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtExceptionFilter, JwtTokenAuthFilter.class).
                oauth2Login((oauth2)->
                        //로그인 필요한 페이지이면, 여기로 보낼 것이다. (프론트에서 처리하도록 하려고함)
                oauth2.loginPage("/login/custom").
                        successHandler( testHandler)
                        .userInfoEndpoint((us)->us.userService(horokOAuthUserService))
                        .failureHandler(horokAuthenticationFailureHandler));

      return  http.build();

    }


}

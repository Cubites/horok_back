package com.metamon.horok.config.secs;

import com.metamon.horok.config.secs.oauth.Handler.HorokAuthenticationFailureHandler;
import com.metamon.horok.config.secs.oauth.Handler.HorokSuccessHandler;
import com.metamon.horok.config.secs.oauth.Handler.RestAuthenticationEntryPoint;
import com.metamon.horok.config.secs.oauth.HttpCookieOAuth2AuthorizationRequestRepository;
import com.metamon.horok.config.secs.oauth.horokjwt.JwtExceptionFilter;
import com.metamon.horok.config.secs.oauth.horokjwt.JwtTokenAuthFilters;
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

import java.util.Arrays;
import java.util.Collections;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class HorokSecurityConfig {

        private final HorokOAuthUserService horokOAuthUserService;
        // OAuth2요청 실패시 작동
        private final HorokAuthenticationFailureHandler horokAuthenticationFailureHandler;
        // OAuth2요청 성공시 작동
        private final HorokSuccessHandler horokSuccessHandler;

        private final JwtExceptionFilter jwtExceptionFilter;
        private final JwtTokenAuthFilters jwtTokenAuthFilters;
        private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

        private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

                http.csrf((auth) -> auth.disable())
                                .formLogin((auth) -> auth.disable())
                                .httpBasic((auth) -> auth.disable())
                                .sessionManagement((s) -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

                http.exceptionHandling(ex -> ex.authenticationEntryPoint(restAuthenticationEntryPoint));
                // 리액트 연동을 위한 cors설정
                http.cors(cors -> cors.configurationSource(new CorsConfigurationSource() {
                        @Override
                        public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                                CorsConfiguration config = new CorsConfiguration();
                                config.setAllowedOrigins(Arrays.asList("https://horok.link"));
//                                config.setAllowedOriginPatterns(Collections.singletonList("*"));
                                config.setAllowedMethods(Collections.singletonList("*"));
                                config.setAllowCredentials(true);
                                config.setAllowedHeaders(Collections.singletonList("*"));
                                return config;
                        }
                }));

                http.authorizeHttpRequests((auth) ->

                auth.requestMatchers("/", "/oauth2/**", "/login/**", "/token/**").permitAll()
                                // .anyRequest().authenticated()
                                .anyRequest().permitAll())
                                .addFilterBefore(jwtTokenAuthFilters, UsernamePasswordAuthenticationFilter.class)
                                .addFilterBefore(jwtExceptionFilter, JwtTokenAuthFilters.class).oauth2Login((oauth2) ->

                                oauth2.authorizationEndpoint(authorizationEndpointConfig -> authorizationEndpointConfig
                                                .authorizationRequestRepository(
                                                                httpCookieOAuth2AuthorizationRequestRepository))
                                                .successHandler(horokSuccessHandler)
                                                .userInfoEndpoint((us) -> us.userService(horokOAuthUserService))
                                                .failureHandler(horokAuthenticationFailureHandler));

                return http.build();

        }

}

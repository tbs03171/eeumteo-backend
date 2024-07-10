package com.eeumteo.eeumteo_backend.config;

import com.eeumteo.eeumteo_backend.utils.jwt.JwtAuthenticationFilter;
import com.eeumteo.eeumteo_backend.utils.jwt.JwtProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    JwtAuthenticationFilter jwtAuthenticationFilter(JwtProvider jwtProvider) {
        return new JwtAuthenticationFilter(jwtProvider);
    }

    @Bean
    public SecurityFilterChain accountFilterChain(HttpSecurity http,
                                                  JwtProvider jwtProvider, ObjectMapper objectMapper) throws Exception {
        return setJwtHttpSecurity(http, objectMapper)
                // 인증 필요한 url 등록
                .addFilterBefore(jwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    private HttpSecurity setJwtHttpSecurity(HttpSecurity http, ObjectMapper objectMapper) throws Exception{
        return http
                // REST API -> CSRF 보안 비활성화
                .csrf((csrfConfig) ->
                        csrfConfig.disable())
                // REST API -> Basic 인증 사용하지 않음
                .httpBasic((httpBasicConfig) ->
                        httpBasicConfig.disable())
                // JWT 사용 -> 세션 사용하지 않음
                .sessionManagement((configurer) ->
                        configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 요청에 대한 인가 규칙 설정
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/**").permitAll());
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers("/api/accounts/register", "/api/accounts/login", "/api/accounts/register/**");
    }
}

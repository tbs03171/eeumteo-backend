package com.eeumteo.eeumteo_backend.utils.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtProvider jwtProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;

        // Request Header 에서 JWT 토큰 추출
        String accountToken = JwtExtractor.extractJwt(req);

        // 토큰으로 email, accountId 추출
        String email = jwtProvider.getEmailFromToken(accountToken);
        Long accountId = jwtProvider.getAccountIdFromToken(accountToken);

        request.setAttribute("accountId", accountId);
        request.setAttribute("email", email);

        //
        Authentication authenticate = jwtProvider.authenticate(new UsernamePasswordAuthenticationToken(email, ""));
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        chain.doFilter(request, response);
    }
}

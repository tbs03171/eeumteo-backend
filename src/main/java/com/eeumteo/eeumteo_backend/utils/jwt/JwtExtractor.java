package com.eeumteo.eeumteo_backend.utils.jwt;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 인증 토큰 추출 보조 클래스
 */
public class JwtExtractor {
    public static final String JWT_HEADER_NAME = "Authorization";
    public static final String JWT_PREFIX = "Bearer ";

    /**
     * 인증 header 로부터 토큰 추출하는 메서드
     */
    public static String extractJwt(HttpServletRequest req) {
        String authHeader = req.getHeader(JWT_HEADER_NAME);
        if (authHeader != null && authHeader.startsWith(JWT_PREFIX)) {
            return authHeader.replace(JWT_PREFIX, "");
        }

        // 헤더에 토큰이 없는 경우
        return null;
    }
}

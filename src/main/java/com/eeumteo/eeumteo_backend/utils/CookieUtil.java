package com.eeumteo.eeumteo_backend.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {
    /**
     * 쿠키 유효 기간(default)
     */
    private final int COOKIE_VALIDATION_SECOND = 1000 * 60 * 60 * 48;

    /**
     * 유효기간을 설정할 수 있는 브라우저 쿠키 생성
     * @param name 쿠키 이름
     * @param value 쿠키 값
     * @param maxAge 쿠키 유효 기간
     */
    public ResponseCookie createCookie(String name, String value, int maxAge) {
        return ResponseCookie.from(name, value)
                .httpOnly(true)
                .path("/")
                .secure(true)
                .sameSite("None")
                .maxAge(maxAge)
                .build();
    }

    /**
     * 서버 스펙의 브라우저 쿠키 생성
     * @param name 쿠키 이름
     * @param value 쿠키 값
     */
    public ResponseCookie createCookie(String name, String value) {
        return ResponseCookie.from(name, value)
                .httpOnly(true)
                .path("/")
                .secure(true)
                .sameSite("None")
                .maxAge(COOKIE_VALIDATION_SECOND)
                .build();
    }

    /**
     * @param req http request
     * @param name 쿠키 이름
     */
    public ResponseCookie getCookie(HttpServletRequest req, String name) {
        Cookie[] findCookies = req.getCookies();

        // 전달된 쿠키 없음
        if (findCookies == null) {
        }
        for (Cookie cookie : findCookies) {
            if (cookie.getName().equals(name)) {
                return ResponseCookie.from(name, cookie.getValue()).build();
            }
        }

        // 쿠키 찾을 수 없음
        return null;
    }
}

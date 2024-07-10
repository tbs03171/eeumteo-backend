package com.eeumteo.eeumteo_backend.utils.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtProvider implements AuthenticationProvider {

    private final AccountDetailsService accountDetailsService;

    /**
     * 서버 스펙 JWT 유효기간
     */
    private static final long TOKEN_VALIDATION_SECOND = 1000L * 60 * 120;
    private static final long REFRESH_TOKEN_VALIDATION_TIME = 1000L * 60 * 60 * 48;

    /**
     * 서버 스펙 JWT 이름
     */
    public static final String TOKEN_NAME = "account_refresh_token";

    @Value("${spring.jwt.secret}")
    private String SECRET_KEY;

    public String generateToken(String email, String accountId) {
        Map<String, String> payload = new HashMap<>();
        payload.put("email", email);
        payload.put("account_id", accountId);
        return doGenerateToken(TOKEN_VALIDATION_SECOND, payload);
    }

    public String generateRefreshToken(String email, String accountId) {
        Map<String, String> payload = new HashMap<>();
        payload.put("email", email);
        payload.put("account_id", accountId);
        return doGenerateToken(REFRESH_TOKEN_VALIDATION_TIME, payload);
    }

    /**
     *
     */
    private String doGenerateToken(long expireTime, Map<String, String> payload) {
        return JWT.create()
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + expireTime))
                .withPayload(payload)
                .sign(getSigningKey(SECRET_KEY));
    }

    /**
     *
     */
    private Algorithm getSigningKey(String secretKey) {
        return Algorithm.HMAC256(secretKey);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        AccountDetails accountDetails = (AccountDetails) accountDetailsService.loadUserByUsername((String) authentication.getPrincipal());

       return new UsernamePasswordAuthenticationToken(
               accountDetails.getEmail(),
               accountDetails.getPassword(),
               accountDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }

    public String getEmailFromToken(String token) {
        DecodedJWT verifiedToken = validateToken(token, getAccountTokenValidator());
        String result = verifiedToken.getClaim("email").asString();
        return result;
    }

    public Long getAccountIdFromToken(String token) {
        DecodedJWT verifiedToken = validateToken(token, getAccountTokenValidator());
        String result = verifiedToken.getClaim("account_id").asString();
        return Long.parseLong(result);
    }

    private DecodedJWT validateToken(String token, JWTVerifier validator) {
        return validator.verify(token);
    }

    private JWTVerifier getAccountTokenValidator() {
        return JWT.require(getSigningKey(SECRET_KEY))
                .withClaimPresence("email")
                .withClaimPresence("account_id")
                .build();
    }
}

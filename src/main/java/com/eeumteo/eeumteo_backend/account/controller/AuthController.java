package com.eeumteo.eeumteo_backend.account.controller;

import com.eeumteo.eeumteo_backend.account.dto.SignInAccountForm;
import com.eeumteo.eeumteo_backend.account.dto.SignInAccountResponse;
import com.eeumteo.eeumteo_backend.account.dto.SignUpAccountForm;
import com.eeumteo.eeumteo_backend.account.dto.SignUpAccountResponse;
import com.eeumteo.eeumteo_backend.account.entity.Account;
import com.eeumteo.eeumteo_backend.account.service.AuthService;
import com.eeumteo.eeumteo_backend.utils.ApiUtil;
import com.eeumteo.eeumteo_backend.utils.ApiUtil.ApiSuccessResult;
import com.eeumteo.eeumteo_backend.utils.CookieUtil;
import com.eeumteo.eeumteo_backend.utils.jwt.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accounts")
public class AuthController {

    private final AuthService authService;
    private final JwtProvider jwtProvider;
    private final CookieUtil cookieUtil;

    /**
     * 계정 가입
     */
    @PostMapping("/register")
    public ApiSuccessResult<SignUpAccountResponse> signUpAccount(@RequestBody @Valid SignUpAccountForm form) {
        Long id = authService.signUpAccount(form);
        return ApiUtil.success(new SignUpAccountResponse(id));
    }

    /**
     * 계정 로그인
     */
    @PostMapping("/login")
    public ApiSuccessResult<SignInAccountResponse> signInAccount(
            @RequestBody @Valid SignInAccountForm form,
            HttpServletRequest req,
            HttpServletResponse res) {
        Account account = authService.signInAccount(form);

        // 토큰 발급
        String accessToken = jwtProvider.generateToken(account.getEmail(), account.getId() + "");
        String refreshToken = jwtProvider.generateRefreshToken(account.getEmail(), account.getId() + "");

        // 쿠키
        ResponseCookie cookie = cookieUtil.createCookie(JwtProvider.TOKEN_NAME, refreshToken);
        res.addHeader("Set-Cookie", cookie.toString());

        ApiSuccessResult<SignInAccountResponse> result = ApiUtil
                .success(new SignInAccountResponse(account, accessToken, refreshToken));
        return result;
    }
}

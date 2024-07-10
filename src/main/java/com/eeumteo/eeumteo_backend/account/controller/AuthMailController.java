package com.eeumteo.eeumteo_backend.account.controller;

import com.eeumteo.eeumteo_backend.account.dto.CodeMailDto;
import com.eeumteo.eeumteo_backend.account.dto.CodeVerificationDto;
import com.eeumteo.eeumteo_backend.account.service.AuthMailService;
import com.eeumteo.eeumteo_backend.utils.ApiUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accounts/register")
@RequiredArgsConstructor
public class AuthMailController {

    private final AuthMailService authMailService;

    /**
     * 이메일 인증 코드 전송
     */
    @PostMapping("/send-code")
    public ApiUtil.ApiSuccessResult<String> sendCode(@RequestBody @Valid CodeMailDto dto) {
        authMailService.sendCodeToEmail(dto.getEmail());
        return ApiUtil.success("성공적으로 메일이 발송되었습니다.");
    }

    /**
     * 이메일 인증 코드 입력
     */
    @PostMapping("/verify-code")
    public ApiUtil.ApiSuccessResult<String> verifyCode(@RequestBody @Valid CodeVerificationDto verification) {
        authMailService.verifySignUpCode(verification.getEmail(), verification.getCode());
        return ApiUtil.success("인증이 완료되었습니다.");
    }
}

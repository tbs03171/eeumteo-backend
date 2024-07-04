package com.eeumteo.eeumteo_backend.account.service;

import com.eeumteo.eeumteo_backend.account.entity.Account;
import com.eeumteo.eeumteo_backend.account.repository.AccountRepository;
import com.eeumteo.eeumteo_backend.utils.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthMailService {

    private static final String AUTH_CODE_PREFIX = "AuthCode ";

    private final AccountRepository accountRepository;

    private final RedisService redisService;

    @Value("${spring.mail.auth-code-expiration-millis}")
    private long authCodeExpirationMillis;

    /**
     * 인증 코드 이메일 전송
     */
    public void sendCodeToEmail(String toEmail) {
        checkDuplicatedEmail(toEmail);
        String title = "이음터 이메일 인증 번호";
        String authCode = createCode();
        sendEmail(toEmail, title, authCode);
        // 이메일 인증 요청 시 인증 번호 Redis에 저장 ( key = "AuthCode " + Email / value = AuthCode )
        redisService.setValues(AUTH_CODE_PREFIX + toEmail,
                authCode, Duration.ofMillis(this.authCodeExpirationMillis));
    }

    /**
     * 해당 이메일로 이미 가입한 회원이 있는지 확인
     */
    private void checkDuplicatedEmail(String email) {
        Optional<Account> account = accountRepository.findByEmail(email);
        if (account.isPresent()) {
            log.debug("MemberServiceImpl.checkDuplicatedEmail exception occur email: {}", email);
            throw new RuntimeException();
        }
    }

    /**
     * 인증 코드 검증
     */
    public boolean verifySignUpCode(String email, String authCode) {
        this.checkDuplicatedEmail(email);
        String redisAuthCode = redisService.getValues(AUTH_CODE_PREFIX + email);
        boolean authResult = redisService.checkExistsValue(redisAuthCode) && redisAuthCode.equals(authCode);

        return authResult;
    }
    private final JavaMailSender emailSender;

    // 메일 발송
    public void sendEmail(String toEmail,
                          String title,
                          String text) {
        SimpleMailMessage emailForm = createEmailForm(toEmail, title, text);
        emailSender.send(emailForm);
    }

    /**
     * 6자리 랜덤 인증 코드 생성해서 반환
     */
    private String createCode() {
        int length = 6;
        try {
            Random random = SecureRandom.getInstanceStrong();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < length; i++) {
                builder.append(random.nextInt(10));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 발신할 이메일 데이터 셋팅
     */
    private SimpleMailMessage createEmailForm(String toEmail,
                                              String title,
                                              String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(title);
        message.setText(text);

        return message;
    }
}

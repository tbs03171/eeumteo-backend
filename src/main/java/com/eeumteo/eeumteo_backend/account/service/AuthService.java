package com.eeumteo.eeumteo_backend.account.service;

import com.eeumteo.eeumteo_backend.account.dto.SignInAccountForm;
import com.eeumteo.eeumteo_backend.account.dto.SignUpAccountForm;
import com.eeumteo.eeumteo_backend.account.entity.Account;
import com.eeumteo.eeumteo_backend.account.entity.Gender;
import com.eeumteo.eeumteo_backend.account.entity.Profile;
import com.eeumteo.eeumteo_backend.account.entity.Role;
import com.eeumteo.eeumteo_backend.account.repository.AccountRepository;
import com.eeumteo.eeumteo_backend.utils.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final AccountRepository accountRepository;

    @Transactional
    public Long signUpAccount(SignUpAccountForm form) {
        // 프로필 생성
        Profile profile = Profile.builder()
                .name(form.getName())
                .gender(Gender.from(form.getGender()))
                .birthdate(form.getBirthdate())
                .build();

        // 계정 생성
        Account account = Account.builder()
                .email(form.getEmail())
                .password(form.getPassword())
                .role(Role.ROLE_USER)
                .profile(profile)
                .build();

        Account saved = accountRepository.save(account);
        return saved.getId();
    }

    public Account signInAccount(SignInAccountForm form) {
        Optional<Account> findAccount = accountRepository.findByEmail(form.getEmail());

        // 해당 계정 존재하지 않는 경우
        if (findAccount.isPresent()) {

        }

        // 비밀번호 잘못된 경우
        if (!form.getPassword().equals(findAccount.get().getPassword())) {

        }

        return findAccount.get();
    }
}

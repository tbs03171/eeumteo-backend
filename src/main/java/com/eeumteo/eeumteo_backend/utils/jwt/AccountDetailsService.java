package com.eeumteo.eeumteo_backend.utils.jwt;

import com.eeumteo.eeumteo_backend.account.entity.Account;
import com.eeumteo.eeumteo_backend.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> accountEntity = accountRepository.findByEmail(username);
        return new AccountDetails(accountEntity.get());
    }
}
package com.eeumteo.eeumteo_backend.utils.jwt;

import com.eeumteo.eeumteo_backend.account.entity.Account;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AccountDetails extends User {

    public AccountDetails(Account account) {
        super(account.getEmail(), account.getPassword(), AuthorityUtils.createAuthorityList(account.getRole().toString()));
    }

    public String getEmail() {
        return this.getUsername();
    }
}

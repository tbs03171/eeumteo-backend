package com.eeumteo.eeumteo_backend.account.dto;

import com.eeumteo.eeumteo_backend.account.entity.Account;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInAccountResponse {
    @NotNull
    private Long accountId;
    @NotNull
    private String accessToken;
    @NotNull
    private String refreshToken;

    public SignInAccountResponse(Account account, String accessToken, String refreshToken) {
        this.accountId = account.getId();
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}

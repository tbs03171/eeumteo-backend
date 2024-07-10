package com.eeumteo.eeumteo_backend.account.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodeVerificationDto {
    @NotNull
    private String email;
    @NotNull
    private String code;
}

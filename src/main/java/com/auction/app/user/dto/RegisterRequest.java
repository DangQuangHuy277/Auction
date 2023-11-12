package com.auction.app.user.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RegisterRequest {
    @NotNull
    private String username;
    @NotNull
    private String email;
    @NotNull
    private String password;
    private String phone;
    @NotNull
    private String role;
}

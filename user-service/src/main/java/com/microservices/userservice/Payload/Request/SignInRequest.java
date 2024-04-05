package com.microservices.userservice.Payload.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SignInRequest {
    @NotNull
    @NotBlank(message = "Email khong duoc bo trong")
    private String email;

    @NotNull
    @NotBlank(message = "Password khong duoc bo trong")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

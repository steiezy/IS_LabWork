package com.lab.airbnb.domain.vo;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NonNull;

@Data
public class RegistrationVo {
    @NotNull
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;
    @NotNull
    @NotBlank
    @Size(min = 6, max = 32)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,}$", message = "Password must contain at least one uppercase letter, one lowercase letter and one number")
    private String password;
    @NotNull
    @NotBlank
    @Pattern(regexp = "^\\d{11}$", message = "Phone number must be 11 digits")
    private String phoneNum;
    @NotNull
    @NotBlank
    @Email
    private String email;

}

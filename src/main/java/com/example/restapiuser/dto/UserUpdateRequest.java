package com.example.restapiuser.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserUpdateRequest(

        @Size(max = 100, message = "비밀번호는 최대100자")
        String passwd,

        @NotBlank(message = "이름은 필수입니다.")
        @Size(max = 100, message = "이름은 최대100자")
        String username,

        @Email(message = "이메일 형식이 틀렸습니다.")
        @Size(max = 200, message = "이메일은 최대 200자")
        String email
) {
}

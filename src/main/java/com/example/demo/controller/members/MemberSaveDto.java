package com.example.demo.controller.members;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MemberSaveDto {
    @NotBlank
    private String userId;
    @NotBlank
    private String password;
    @NotBlank
    private String checkPassword;
    @NotBlank
    private String username;
    @NotBlank
    @Email
    private String email;
}

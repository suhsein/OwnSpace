package com.example.demo.controller.members;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MemberDto {
    @NotBlank
    private String userId;
    @NotBlank
    private String password;
}

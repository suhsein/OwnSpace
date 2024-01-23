package com.example.demo.domain.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class Member {
    private Long id;
    private String userId;
    private String password;
    private String username;
    private String email;
}

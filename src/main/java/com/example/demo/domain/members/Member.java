package com.example.demo.domain.members;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Member {
    private Long id;
    private String userId;
    private String password;
    private String username;
    private String email;

    public Member() {
    }

    @Builder
    public Member(String userId, String password, String username, String email) {
        this.userId = userId;
        this.password = password;
        this.username = username;
        this.email = email;
    }
}

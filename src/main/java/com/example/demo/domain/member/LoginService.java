package com.example.demo.domain.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final MemberRepository memberRepository;

    public Member login(String userId, String password) {
        return memberRepository.findByUserId(userId)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }
}

package com.suhsein.ownspace.service.members;

import com.suhsein.ownspace.domain.members.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginService {
    private final MemberService memberService;

    public Member login(String userId, String password) {
        List<Member> findMember = memberService.findByUserId(userId);
        return findMember.stream().findFirst().orElse(null);
    }
}

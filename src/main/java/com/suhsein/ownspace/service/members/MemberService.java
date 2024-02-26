package com.suhsein.ownspace.service.members;

import com.suhsein.ownspace.domain.members.Member;
import com.suhsein.ownspace.repository.members.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public void save(Member member) {
        memberRepository.save(member);
    }

    public Member findOne(Long id) {
        return memberRepository.findOne(id);
    }

    public List<Member> findByUserId(String userId) {
        return memberRepository.findByUserId(userId);
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }
}

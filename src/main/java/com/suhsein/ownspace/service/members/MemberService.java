package com.suhsein.ownspace.service.members;

import com.suhsein.ownspace.service.members.dto.MemberSaveDto;
import com.suhsein.ownspace.domain.members.Member;
import com.suhsein.ownspace.repository.members.MemberRepository;
import com.suhsein.ownspace.service.members.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

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
    public List<Member> findByEmail(String email){
        return memberRepository.findByEmail(email);
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public Member login(MemberDto member, BindingResult bindingResult) {
        List<Member> findMember = findByUserId(member.getUserId());
        Member loginMember = findMember.stream()
                .findFirst().orElse(null);

        if (loginMember == null) {
            bindingResult.reject("noMember");
            return null;
        } else if(!loginMember.getPassword().equals(member.getPassword())){
            bindingResult.reject("loginFail");
            return null;
        }
        return loginMember;
    }

    public void checkDuplicatedMember(MemberSaveDto memberSaveDto,
                                         BindingResult bindingResult){
        List<Member> byUserId = findByUserId(memberSaveDto.getUserId());

        if(byUserId.stream().findFirst().orElse(null) != null){
            bindingResult.reject("duplicatedId");
        }
        List<Member> byEmail = findByEmail(memberSaveDto.getEmail());
        if (byEmail.stream().findFirst().orElse(null) != null) {
            bindingResult.reject("duplicatedEmail");
        }
    }
}

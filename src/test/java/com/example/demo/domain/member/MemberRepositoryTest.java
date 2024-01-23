package com.example.demo.domain.member;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * memory member repository 테스트
 */
@Slf4j
class MemberRepositoryTest {
    private final MemberRepository memberRepository = new MemberRepository();

    @BeforeEach
    void beforeEach(){
        memberRepository.clearStore();
    }

    @Test
    void save() {
        Member member = makeMember();
        Member savedMember = memberRepository.save(member);
        log.info("savedMemberName={}", savedMember.getUsername());
        assertThat(savedMember.getUsername()).isEqualTo(member.getUsername());
    }

    @Test
    void findById() {
        Member member = makeMember();
        memberRepository.save(member);
        log.info("member id={}", member.getId());
        assertThat(memberRepository.findById(member.getId())).isEqualTo(member);
    }

    @Test
    void findAll() {
        // given
        Member member1 = makeMember();
        Member member2 = new Member();
        member2.setUserId("aaa");
        member2.setUsername("springboot");
        member2.setPassword("456");

        memberRepository.save(member1);
        memberRepository.save(member2);
        // when
        List<Member> members = memberRepository.findAll();
        // then
        members.forEach(member-> {
            log.info("member username={}", member.getUsername());
        });
        assertThat(members.size()).isEqualTo(2);
    }

    private Member makeMember() {
        Member member = new Member();
        member.setUserId("abc");
        member.setUsername("spring");
        member.setPassword("123");
        return member;
    }
}
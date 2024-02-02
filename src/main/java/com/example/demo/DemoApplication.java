package com.example.demo;

import com.example.demo.configuration.S3Config;
import com.example.demo.domain.members.Member;
import com.example.demo.repository.members.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Import;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@RequiredArgsConstructor
@Import(S3Config.class)
public class DemoApplication {
	private final MemberRepository memberRepository;
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void initData(){
		Member member = new Member();
		member.setUserId("aaa");
		member.setPassword("aaa");
		member.setEmail("aaa@naver.com");
		memberRepository.save(member);
	}
}

package com.suhsein.ownspace;

import com.suhsein.ownspace.configuration.S3Config;
import com.suhsein.ownspace.domain.members.Member;
import com.suhsein.ownspace.service.daily.DailyService;
import com.suhsein.ownspace.service.members.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Import;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@Import(S3Config.class)
@RequiredArgsConstructor
public class OwnSpaceApplication {
	private final MemberService memberService;
	public static void main(String[] args) {
		SpringApplication.run(OwnSpaceApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void initData(){

	}
}

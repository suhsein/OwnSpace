package com.suhsein.ownspace;

import com.suhsein.ownspace.configuration.S3Config;
import com.suhsein.ownspace.service.daily.DailyService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(S3Config.class)
@RequiredArgsConstructor
public class OwnSpaceApplication {
	private final DailyService dailyService;
	public static void main(String[] args) {
		SpringApplication.run(OwnSpaceApplication.class, args);
	}

//	@EventListener(ApplicationReadyEvent.class)
//	public void initData(){
//		for (int i = 0; i < 20; i++) {
//			dailyService.save(Daily.builder()
//					.writer(null)
//					.title("제목")
//					.content("내용")
//					.createDate(LocalDateTime.now())
//					.build());
//		}
//	}
}

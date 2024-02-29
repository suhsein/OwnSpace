package com.suhsein.ownspace;

import com.suhsein.ownspace.configuration.S3Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(S3Config.class)
public class OwnSpaceApplication {
	public static void main(String[] args) {
		SpringApplication.run(OwnSpaceApplication.class, args);
	}
}

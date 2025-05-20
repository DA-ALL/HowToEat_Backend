package com.daall.howtoeat;

import com.daall.howtoeat.client.user.UserRepository;
import com.daall.howtoeat.client.user.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HowToEatApplication {
	public static void main(String[] args) {
		SpringApplication.run(HowToEatApplication.class, args);
	}

}

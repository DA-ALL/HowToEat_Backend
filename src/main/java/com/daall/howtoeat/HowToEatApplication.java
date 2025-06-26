package com.daall.howtoeat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class HowToEatApplication {
	public static void main(String[] args) {
		SpringApplication.run(HowToEatApplication.class, args);
	}

}

package com.metamon.horok;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HorokApplication {

	public static void main(String[] args) {
		SpringApplication.run(HorokApplication.class, args);
	}

}

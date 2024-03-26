package com.metamon.horok;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableRedisRepositories(basePackageClasses = com.metamon.horok.repository.TokenInfoRedisRepository.class)
public class HorokApplication {

	public static void main(String[] args) {
		SpringApplication.run(HorokApplication.class, args);
	}

}

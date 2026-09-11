package com.r3vision;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class OtpService1Application {

	public static void main(String[] args) {
		SpringApplication.run(OtpService1Application.class, args);
	}

}

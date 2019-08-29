package com.example.phonebookfront;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
public class PhoneBookFrontApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhoneBookFrontApplication.class, args);
	}
}

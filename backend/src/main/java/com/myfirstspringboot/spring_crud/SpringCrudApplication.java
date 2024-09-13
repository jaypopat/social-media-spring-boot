package com.myfirstspringboot.spring_crud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.myfirstspringboot.spring_crud.model")
public class SpringCrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCrudApplication.class, args);
	}

}

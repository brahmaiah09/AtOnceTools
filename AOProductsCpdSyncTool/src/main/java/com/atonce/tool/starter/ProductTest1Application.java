package com.atonce.tool.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
@ComponentScan(basePackages={"com.atonce.tool.controller","com.atonce.tool.service","com.atonce.tool.dao"})
@SpringBootApplication
public class ProductTest1Application {

	public static void main(String[] args) {
		SpringApplication.run(ProductTest1Application.class, args);
	}
}

package com.atonce.tools;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"com.atonce.tool.controller","com.atonce.tool.service","com.atonce.tool.dao"})
@SpringBootApplication
public class AoProductsCpdSyncToolApplication {

	public static void main(String[] args) {
		SpringApplication.run(AoProductsCpdSyncToolApplication.class, args);
	}
}

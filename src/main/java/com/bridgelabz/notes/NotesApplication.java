package com.bridgelabz.notes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class NotesApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(NotesApplication.class, args);
		log.info("Notes App Started in {} Environment", context.getEnvironment().getProperty("environment"));
		System.out.println("Welcome to Notes App..!!");
	}

}

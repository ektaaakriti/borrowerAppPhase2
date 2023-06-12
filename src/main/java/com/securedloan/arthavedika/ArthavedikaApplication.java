package com.securedloan.arthavedika;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;

@OpenAPIDefinition
@SpringBootApplication
public class ArthavedikaApplication extends SpringBootServletInitializer implements ApplicationRunner {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ArthavedikaApplication.class);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		final Logger log = LoggerFactory.getLogger(ArthavedikaApplication.class);

		log.info("Application started with command-line arguments: {}", Arrays.toString(args.getSourceArgs()));
	}

	public static void main(String[] args) {

		SpringApplication.run(ArthavedikaApplication.class, args);

	}

}

package com.mercadolibre.mutantverifier;

import com.mercadolibre.mutantverifier.dto.StatsDto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.WebApplicationInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

@SpringBootApplication
public class MutantVerifierApplication  {

	public static void main(String[] args) {
		SpringApplication.run(MutantVerifierApplication.class, args);
	}


}

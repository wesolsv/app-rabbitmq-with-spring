package com.wszd.analisecredito;

import com.wszd.analisecredito.service.AnaliseCreditoService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@AllArgsConstructor
public class AnalisecreditoApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnalisecreditoApplication.class, args);
	}

}

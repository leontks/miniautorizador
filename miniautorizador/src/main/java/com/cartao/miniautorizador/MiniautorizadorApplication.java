package com.cartao.miniautorizador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.cartao.miniautorizador")
public class MiniautorizadorApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiniautorizadorApplication.class, args);
	}

}

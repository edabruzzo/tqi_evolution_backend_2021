package br.com.abruzzo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAuthorizationServer
@EnableResourceServer
public class ServidorAutenticacaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServidorAutenticacaoApplication.class, args);
	}

}

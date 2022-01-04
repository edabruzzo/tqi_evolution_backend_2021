package br.com.abruzzo;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@SpringBootApplication
@EnableAuthorizationServer
@EnableResourceServer
@RestController
public class ServidorAutenticacaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServidorAutenticacaoApplication.class, args);
	}


	@RequestMapping("/user")
	public Principal user(Principal user){
		return user;
	}

	@Bean
	public ModelMapper modelMapper () {return new ModelMapper();}

}

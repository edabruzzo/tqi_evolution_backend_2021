package br.com.abruzzo.emprestimo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class EmprestimoApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmprestimoApplication.class, args);
	}

}

package br.com.abruzzo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ClienteApplication {
    public static void main(String[] args) {SpringApplication.run(ClienteApplication.class, args);}
}

package br.com.abruzzo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
@EnableEurekaClient
@EnableAutoConfiguration
public class ClienteApplication {


    /**
     * Método para criar um Bean injetável do RestTemplate
     * Necessário pois estamos utilizando o Eureka como Server Discover e, portanto,
     * precisamos da anotação {@link LoadBalanced} para o microservice Cliente reconhecer o microservice
     * "Emprestimo"
     *
     * @return RestTemplate com LoadBalance
     */
    @Bean
    @LoadBalanced
    RestTemplate getRestTemplate(){
        return new RestTemplate();
    }


    public static void main(String[] args) {SpringApplication.run(ClienteApplication.class, args);}

}

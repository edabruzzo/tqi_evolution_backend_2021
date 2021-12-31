package br.com.abruzzo;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @link https://github.com/spring-cloud/spring-cloud-netflix/blob/main/spring-cloud-netflix-eureka-client/src/main/java/org/springframework/cloud/netflix/eureka/reactive/EurekaReactiveDiscoveryClientConfiguration.java
 *
 * @author Emmanuel Abruzzo
 * @date 25/12/2021
 */
@SpringBootApplication
@EnableEurekaClient
@EnableAutoConfiguration
@EnableCircuitBreaker
@EnableFeignClients
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

    @Bean
    ModelMapper modelMapper () {return new ModelMapper();}




    public static void main(String[] args) {SpringApplication.run(ClienteApplication.class, args);}





}

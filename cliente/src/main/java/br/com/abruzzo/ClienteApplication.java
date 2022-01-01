package br.com.abruzzo;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
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
@EnableResourceServer
public class ClienteApplication {

    public static void main(String[] args) {SpringApplication.run(ClienteApplication.class, args);}

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
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

    @Bean
    public ModelMapper modelMapper () {return new ModelMapper();}


    @Bean
    public RequestInterceptor getRequestInterceptor(){
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

                if(authentication==null)
                    return;

                OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
                requestTemplate.header("Authorization","Bearer"+details.getTokenValue());

            }
        }   ;
    }













}

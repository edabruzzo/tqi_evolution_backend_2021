package br.com.abruzzo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableRedisRepositories
@EnableEurekaClient
public class ServicoSolicitacaoEmprestimoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicoSolicitacaoEmprestimoApplication.class, args);
	}


	/**
	 * Método para criar um Bean injetável do RestTemplate
	 * Necessário pois estamos utilizando o Eureka como Server Discover e, portanto,
	 * precisamos da anotação {@link LoadBalanced} para o microservice ServicosSolicitacaoEmprestimo reconhecer o microservice
	 * Gerenciamento de "Emprestimo" -> que se destina apenas a empréstimos aprovados.
	 *
	 * @return RestTemplate com LoadBalance
	 */
	@Bean
	@LoadBalanced
	RestTemplate getRestTemplate(){
		return new RestTemplate();
	}


	@Bean
	JedisConnectionFactory jedisConnectionFactory() {
		JedisConnectionFactory jedisConFactory = new JedisConnectionFactory();
		jedisConFactory.setHostName("localhost");
		jedisConFactory.setPort(6379);
		return jedisConFactory;
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(jedisConnectionFactory());
		return template;
	}


}
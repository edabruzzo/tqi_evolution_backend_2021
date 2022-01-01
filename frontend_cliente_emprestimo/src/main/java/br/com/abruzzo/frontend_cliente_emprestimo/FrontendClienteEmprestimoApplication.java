package br.com.abruzzo.frontend_cliente_emprestimo;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
public class FrontendClienteEmprestimoApplication {

	public static void main(String[] args) {
		SpringApplication.run(FrontendClienteEmprestimoApplication.class, args);
	}



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

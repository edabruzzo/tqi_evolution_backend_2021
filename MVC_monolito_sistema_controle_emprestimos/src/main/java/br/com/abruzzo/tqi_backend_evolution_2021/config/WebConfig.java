package br.com.abruzzo.tqi_backend_evolution_2021.config;

import br.com.abruzzo.tqi_backend_evolution_2021.interceptors.InterceptadorAcesso;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author Emmanuel Abruzzo
 * @date 06/01/2022
 */
@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new InterceptadorAcesso()).addPathPatterns("/**");
    }
}

package br.com.abruzzo.tqi_backend_evolution_2021;


import br.com.abruzzo.tqi_backend_evolution_2021.util.CriptografiaSenha;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;


/**
 * @author Emmanuel Abruzzo
 * @date 05/01/2022
 */
@EnableCaching
@SpringBootApplication
public class SistemaControleEmprestimosApplication {

    public static void main(String[] args) {
        SpringApplication.run(SistemaControleEmprestimosApplication.class, args);
    }


    @Bean
    ModelMapper modelMapper () {return new ModelMapper();}


}

package br.com.abruzzo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Emmanuel Abruzzo
 * @date 01/01/2022
 */
@Configuration
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter{


    @Value("${usuarioTeste.password}")
    private String usuarioTestePassword;


    @Value("${usuarioTeste}")
    private String usuarioTeste;


    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }


    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        return super.userDetailsService();
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder){
        try {
            authenticationManagerBuilder
                    .inMemoryAuthentication()
                    .passwordEncoder(this.passwordEncoder())
                    .withUser(this.usuarioTeste)
                    .password((passwordEncoder().encode(this.usuarioTestePassword)))
                    .roles("USER");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}

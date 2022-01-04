package br.com.abruzzo.config;

import br.com.abruzzo.model.Role;


import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

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


    @Autowired
    private DataSource dataSource;

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


    public void configureInMemory(AuthenticationManagerBuilder authenticationManagerBuilder){
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


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(encoder);

        UserDetails usuarioInicial = User.withDefaultPasswordEncoder()
                        .username("99999999999")
                        .password("9999")
                        .roles(String.valueOf(Role.FUNCIONARIO))
                        .roles(String.valueOf(Role.SUPER_ADMIN))
                        .build();




    }




}

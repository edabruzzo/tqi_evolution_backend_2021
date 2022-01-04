package br.com.abruzzo.frontend_cliente_emprestimo.config;

import br.com.abruzzo.frontend_cliente_emprestimo.dto.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

/**
 * @author Emmanuel Abruzzo
 * @date 04/01/2022
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .antMatchers("/home/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl(String.format("/home"), true)
                        .permitAll()
                )
                .logout(logout -> {
                    logout.logoutUrl("/logout")
                            .logoutSuccessUrl("/home");
                }).csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        auth
                .jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(encoder);

		UserDetails user =
				 UsuarioDTO.builder()
					.username("9999999999")
					.password(encoder.encode("9999"))
					.roles("SUPER_ADMIN")
					.build();
    }

}


package br.com.abruzzo.tqi_backend_evolution_2021.config;


import br.com.abruzzo.tqi_backend_evolution_2021.util.CriptografiaSenha;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;


import br.com.abruzzo.tqi_backend_evolution_2021.model.Role;
import br.com.abruzzo.tqi_backend_evolution_2021.model.Usuario;
import br.com.abruzzo.tqi_backend_evolution_2021.repository.AutenticacaoUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @link https://stackoverflow.com/questions/52862769/basic-jdbc-authentication-authorization-not-working
 *
 * @author Emmanuel Abruzzo
 * @date 06/01/2022
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
                .antMatchers("/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/cliente", true)
                        .permitAll()
                )
                .logout(logout -> {
                    logout.logoutUrl("/logout")
                            .logoutSuccessUrl("/login");
                }).csrf().disable();
    }


    @Autowired
    AutenticacaoUsuarioRepository autenticacaoUsuarioRepository;


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {


        auth.jdbcAuthentication().dataSource(this.dataSource).usersByUsernameQuery("select username, password, 'true' as enabled from users where username = ?")
                .authoritiesByUsernameQuery("select username, authority from authorities where username = ?")
                .passwordEncoder(CriptografiaSenha.encoder);
    }




    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {


        auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(CriptografiaSenha.encoder);

        String emailAdmin = "superAdmin@gmail.com";
        String senhaCriptografada = CriptografiaSenha.criptografarSenha("9999");

        Usuario usuarioAdmin = this.autenticacaoUsuarioRepository.findByUsername(emailAdmin);


        if(usuarioAdmin == null){

            UserDetails userDetails = User.builder()
                    /**
                     * Uma exigência dos requisitos enviados pela TQI é que o login seja feito pelo email do cliente
                     */
                    .username(emailAdmin)
                    .password(senhaCriptografada)
                    .roles(String.valueOf(Role.FUNCIONARIO),String.valueOf(Role.SUPER_ADMIN))
                    .build();

            List<Role> roles = new ArrayList<>();
            roles.add(Role.FUNCIONARIO);
            roles.add(Role.SUPER_ADMIN);

            usuarioAdmin = new Usuario();
            usuarioAdmin.setUsername(emailAdmin);
            usuarioAdmin.setPassword(senhaCriptografada);
            usuarioAdmin.setRoles(roles);
            usuarioAdmin.setEnabled(true);

            this.autenticacaoUsuarioRepository.save(usuarioAdmin);

        }

        String emailCliente = "cliente@gmail.com";
        String senhaCriptografadaCliente = CriptografiaSenha.criptografarSenha("2222");

        Usuario usuarioCliente = this.autenticacaoUsuarioRepository.findByUsername(emailCliente);


        if(usuarioCliente == null){

            UserDetails userDetails = User.builder()
                    .username(emailCliente)
                    .password(senhaCriptografadaCliente)
                    .roles(String.valueOf(Role.CLIENTE))
                    .build();

            List<Role> roles = new ArrayList<>();
            roles.add(Role.CLIENTE);

            usuarioCliente = new Usuario();
            usuarioCliente.setUsername(emailCliente);
            usuarioCliente.setPassword(senhaCriptografadaCliente);
            usuarioCliente.setRoles(roles);
            usuarioCliente.setEnabled(true);

            this.autenticacaoUsuarioRepository.save(usuarioCliente);

        }




        String emailFuncionarioSemPrivilegioAdmin = "funcionario@gmail.com";
        String senhaCriptografadaFuncionarioSemPrivilegioAdmin = CriptografiaSenha.criptografarSenha("1111");

        Usuario usuarioFuncionarioSemPrivilegioAdmin = this.autenticacaoUsuarioRepository.findByUsername(emailFuncionarioSemPrivilegioAdmin);


        if(usuarioFuncionarioSemPrivilegioAdmin == null){

            UserDetails userDetails = User.builder()
                    .username(emailFuncionarioSemPrivilegioAdmin)
                    .password(senhaCriptografadaFuncionarioSemPrivilegioAdmin)
                    .roles(String.valueOf(Role.FUNCIONARIO))
                    .build();

            List<Role> roles = new ArrayList<>();
            roles.add(Role.FUNCIONARIO);

            usuarioFuncionarioSemPrivilegioAdmin = new Usuario();
            usuarioFuncionarioSemPrivilegioAdmin.setUsername(emailFuncionarioSemPrivilegioAdmin);
            usuarioFuncionarioSemPrivilegioAdmin.setPassword(senhaCriptografadaFuncionarioSemPrivilegioAdmin);
            usuarioFuncionarioSemPrivilegioAdmin.setRoles(roles);
            usuarioFuncionarioSemPrivilegioAdmin.setEnabled(true);

            this.autenticacaoUsuarioRepository.save(usuarioFuncionarioSemPrivilegioAdmin);

        }


    }



}


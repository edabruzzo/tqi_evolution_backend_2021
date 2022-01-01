package br.com.abruzzo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

/**
 * @author Emmanuel Abruzzo
 * @date 01/01/2022
 */

@Configuration
public class AuthorizationServerConfigures extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${application_client_secret}")
    private String applicationClientSecretAccessKey;

    @Value("${application_client}")
    private String applicationClient;



    public void configure(ClientDetailsServiceConfigurer clients) {
        try {
            clients.inMemory()
                    .withClient(this.applicationClient)
                    .secret(passwordEncoder.encode(this.applicationClientSecretAccessKey))
                    .authorizedGrantTypes("password")
                    .scopes("web");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(this.authenticationManager)
                .userDetailsService(this.userDetailsService);
    }
}

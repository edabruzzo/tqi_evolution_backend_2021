package br.com.abruzzo.tqi_backend_evolution_2021.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author Emmanuel Abruzzo
 * @date 10/01/2022
 */
public class CriptografiaSenha {

    public static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static String criptografarSenha(String senha){
        return encoder.encode(senha);
    }


}

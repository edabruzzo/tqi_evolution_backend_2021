package br.com.abruzzo.tqi_backend_evolution_2021.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author Emmanuel Abruzzo
 * @date 10/01/2022
 */
public class VerificacoesSessao {

    static Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    public static boolean verificaSeUsuarioLogadoCliente() {
        return authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_CLIENTE"));
    }

    public static String verificaEmailUsuarioLogado() {
        return authentication.getName();
    }

    public static boolean verificaSeUsuarioLogadoEhFuncionarioSemPrivilegioAdmin() {
        return ! authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_SUPER_ADMIN"));
    }

    public static String credenciaisUsuarioLogado() {
        return authentication.getCredentials().toString();
    }
}

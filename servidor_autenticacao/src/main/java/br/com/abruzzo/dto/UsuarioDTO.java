package br.com.abruzzo.dto;

import br.com.abruzzo.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Emmanuel Abruzzo
 * @date 03/01/2022
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {


    private Long id;
    private String username;
    private String emailAddress;
    private String password;
    private String status;
    private Integer loginAttempt;

    /**
     * @link Vide: https://stackoverflow.com/questions/37615034/spring-security-spring-boot-how-to-set-roles-for-users/50533455
     */
    private List<String> roles = new ArrayList<>();

}

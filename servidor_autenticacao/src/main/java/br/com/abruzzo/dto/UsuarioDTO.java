package br.com.abruzzo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private List<String> roles = new ArrayList<>();

}

package br.com.abruzzo.security;

import lombok.Builder;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Emmanuel Abruzzo
 * @date 03/01/2022
 */
@Table(name="tb_usuario")
@Entity
@Data
@Builder
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id_usuario;

    @Column(name="username")
    private String username;

    @Column(name="emailAddress")
    private String emailAddress;

    @Column(name="password")
    private String password;

    @Column(name="status")
    private String status;

    @Column(name="tentativasLogin")
    private Integer loginAttempt;

    @ElementCollection(fetch= FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    public Long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Long id_usuario) {
        this.id_usuario = id_usuario;
    }


    public Usuario() {
    }

    public Usuario(Long id, String username, String emailAddress, String password, Character status, Integer loginAttempt, List<String> roles) {
        this.id_usuario = id;
        this.username = username;
        this.emailAddress = emailAddress;
        this.password = password;
        this.status = status;
        this.loginAttempt = loginAttempt;
        this.roles = roles;
    }
}

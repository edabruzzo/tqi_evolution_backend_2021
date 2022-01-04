package br.com.abruzzo.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @link https://www.tutorialspoint.com/spring_boot/spring_boot_oauth2_with_jwt.htm
 *
 * @author Emmanuel Abruzzo
 * @date 03/01/2022
 */
@Table(name="users")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

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



}

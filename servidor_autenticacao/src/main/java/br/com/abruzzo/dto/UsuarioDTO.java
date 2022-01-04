package br.com.abruzzo.dto;

import lombok.Builder;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Emmanuel Abruzzo
 * @date 03/01/2022
 */
public class UsuarioDTO {


    private String username;
    private String emailAddress;
    private String password;
    private Character status;
    private Integer loginAttempt;
    private List<String> roles = new ArrayList<>();

    public UsuarioDTO() { }

    public UsuarioDTO(String username, String emailAddress, String password, Character status, Integer loginAttempt, List<String> roles) {
        this.username = username;
        this.emailAddress = emailAddress;
        this.password = password;
        this.status = status;
        this.loginAttempt = loginAttempt;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Character getStatus() {
        return status;
    }

    public void setStatus(Character status) {
        this.status = status;
    }

    public Integer getLoginAttempt() {
        return loginAttempt;
    }

    public void setLoginAttempt(Integer loginAttempt) {
        this.loginAttempt = loginAttempt;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}

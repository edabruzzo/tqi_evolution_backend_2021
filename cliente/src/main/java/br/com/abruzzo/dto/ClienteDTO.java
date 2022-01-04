package br.com.abruzzo.dto;

import javax.persistence.Column;
import javax.validation.constraints.Pattern;

/**
 * @author Emmanuel Abruzzo
 * @date 31/12/2021
 */
public class ClienteDTO {


    private Long id;
    private String nome;
    private String email;
    private String cpf;
    private String rg;
    private String enderecoCompleto;
    private Double renda;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getEnderecoCompleto() {
        return enderecoCompleto;
    }

    public void setEnderecoCompleto(String enderecoCompleto) {
        this.enderecoCompleto = enderecoCompleto;
    }

    public Double getRenda() {
        return renda;
    }

    public void setRenda(Double renda) {
        this.renda = renda;
    }


    public ClienteDTO() {
    }

    public ClienteDTO(Long id, String nome, String email, String cpf, String rg, String enderecoCompleto, Double renda) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.rg = rg;
        this.enderecoCompleto = enderecoCompleto;
        this.renda = renda;
    }
}

package br.com.abruzzo.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Cliente {


    private String nome;
    private String email;
    private String CPF;
    private String RG;
    private String endereçoCompleto;
    private Double renda;
    private String senha;


    public Cliente() {
    }

    public Cliente(String nome, String email, String CPF, String RG, String endereçoCompleto, Double renda, String senha) {
        this.nome = nome;
        this.email = email;
        this.CPF = CPF;
        this.RG = RG;
        this.endereçoCompleto = endereçoCompleto;
        this.renda = renda;
        this.senha = senha;
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

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getRG() {
        return RG;
    }

    public void setRG(String RG) {
        this.RG = RG;
    }

    public String getEndereçoCompleto() {
        return endereçoCompleto;
    }

    public void setEndereçoCompleto(String endereçoCompleto) {
        this.endereçoCompleto = endereçoCompleto;
    }

    public Double getRenda() {
        return renda;
    }

    public void setRenda(Double renda) {
        this.renda = renda;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }


    @Override
    public String toString() {
        return "Cliente{" +
                "nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", CPF='" + CPF + '\'' +
                ", RG='" + RG + '\'' +
                ", endereçoCompleto='" + endereçoCompleto + '\'' +
                ", renda=" + renda +
                ", senha='" + senha + '\'' +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(nome, cliente.nome) && Objects.equals(email, cliente.email) && Objects.equals(CPF, cliente.CPF) && Objects.equals(RG, cliente.RG) && Objects.equals(endereçoCompleto, cliente.endereçoCompleto) && Objects.equals(renda, cliente.renda) && Objects.equals(senha, cliente.senha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, email, CPF, RG, endereçoCompleto, renda, senha);
    }
}

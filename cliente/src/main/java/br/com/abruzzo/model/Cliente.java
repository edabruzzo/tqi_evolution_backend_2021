package br.com.abruzzo.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


@Entity
@Table(name = "tb_cliente", catalog = "cliente", schema = "public")
//@Data //Using @Data for JPA entities is not recommended. It can cause severe performance and memory consumption issues.
@NoArgsConstructor
public class Cliente implements Serializable {


    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Column(name="nome")
    private String nome;

    @Column(name="email")
    private String email;

    @Column(name="CPF")
    private String CPF;

    @Column(name="RG")
    private String RG;

    @Column(name="endereçoCompleto")
    private String endereçoCompleto;

    @Column(name="renda")
    private Double renda;

    @Column(name="senha")
    private String senha;


    public Cliente(Long id, String nome, String email, String CPF, String RG, String endereçoCompleto, Double renda, String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.CPF = CPF;
        this.RG = RG;
        this.endereçoCompleto = endereçoCompleto;
        this.renda = renda;
        this.senha = senha;
    }


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
                "id=" + id +
                ", nome='" + nome + '\'' +
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
        return Objects.equals(id, cliente.id) && Objects.equals(nome, cliente.nome) && Objects.equals(email, cliente.email) && Objects.equals(CPF, cliente.CPF) && Objects.equals(RG, cliente.RG) && Objects.equals(endereçoCompleto, cliente.endereçoCompleto) && Objects.equals(renda, cliente.renda) && Objects.equals(senha, cliente.senha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, email, CPF, RG, endereçoCompleto, renda, senha);
    }
}

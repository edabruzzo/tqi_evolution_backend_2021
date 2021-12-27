package br.com.abruzzo.model;


import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Objects;

/**
 * Classe de entidade JPA
 * Bean validation utilizando JAVAEE7 para validação dos campos da entidade
 *
 * @link https://docs.oracle.com/javaee/7/tutorial/bean-validation001.htm
 * @link https://github.com/javaee-samples/javaee7-samples/blob/master/pom.xml
 * @link https://guilhermesteves.dev/tutoriais/regex-uteis-para-o-seu-dia-a-dia/
 *
 * @author Emmanuel Abruzzo
 * @date 25/12/2021
 */
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
    @Pattern(regexp="(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
    private String email;

    @Column(name="cpf")
    @Pattern(regexp="([0-9]{2}[\\.]?[0-9]{3}[\\.]?[0-9]{3}[\\/]?[0-9]{4}[-]?[0-9]{2})|([0-9]{3}[\\.]?[0-9]{3}[\\.]?[0-9]{3}[-]?[0-9]{2})\n")
    private String cpf;

    @Column(name="rg")
    @Pattern(regexp="(^\\d{1,2}).?(\\d{3}).?(\\d{3})-?(\\d{1}|X|x$)")
    private String rg;

    @Column(name="enderecoCompleto")
    private String enderecoCompleto;

    @Column(name="renda")
    private Double renda;

    @Column(name="senha")
    @Pattern(regexp="(?=^.{6,}$)((?=.*\\w)(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[|!\"$%&\\/\\(\\)\\?\\^\\'\\\\\\+\\-\\*]))^.*")
    private String senha;  // Senha de no mínimo 6 caracteres, pelo menos uma letra maiúscula, pelo menos uma letra minúscula, pelo menos um número, pelo menos um caractere especial


    public Cliente(Long id, String nome, String email, String cpf, String rg, String enderecoCompleto, Double renda, String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.rg = rg;
        this.enderecoCompleto = enderecoCompleto;
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
                ", CPF='" + cpf + '\'' +
                ", RG='" + rg + '\'' +
                ", enderecoCompleto='" + enderecoCompleto + '\'' +
                ", renda=" + renda +
                ", senha='" + senha + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(id, cliente.id) && Objects.equals(nome, cliente.nome) && Objects.equals(email, cliente.email) && Objects.equals(cpf, cliente.cpf) && Objects.equals(rg, cliente.rg) && Objects.equals(enderecoCompleto, cliente.enderecoCompleto) && Objects.equals(renda, cliente.renda) && Objects.equals(senha, cliente.senha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, email, cpf, rg, enderecoCompleto, renda, senha);
    }
}

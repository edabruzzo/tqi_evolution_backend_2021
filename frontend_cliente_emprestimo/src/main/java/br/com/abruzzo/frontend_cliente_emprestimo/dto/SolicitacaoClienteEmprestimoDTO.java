package br.com.abruzzo.dto;

import java.sql.Date;

/**
 * @author Emmanuel Abruzzo
 * @date 02/01/2022
 */
public class SolicitacaoClienteEmprestimoDTO {


    private String nome;
    private String email;
    private String cpf;
    private String rg;
    private String enderecoCompleto;
    private Double renda;
    private String senha;
    private Double valor;
    private Date data_primeira_parcela;
    private Integer numeroMaximoParcelas;


    public SolicitacaoClienteEmprestimoDTO() {
    }

    public SolicitacaoClienteEmprestimoDTO(String nome, String email, String cpf, String rg, String enderecoCompleto, Double renda, String senha, Double valor, Date data_primeira_parcela, Integer numeroMaximoParcelas) {
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.rg = rg;
        this.enderecoCompleto = enderecoCompleto;
        this.renda = renda;
        this.senha = senha;
        this.valor = valor;
        this.data_primeira_parcela = data_primeira_parcela;
        this.numeroMaximoParcelas = numeroMaximoParcelas;
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

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Date getData_primeira_parcela() {
        return data_primeira_parcela;
    }

    public void setData_primeira_parcela(Date data_primeira_parcela) {
        this.data_primeira_parcela = data_primeira_parcela;
    }

    public Integer getNumeroMaximoParcelas() {
        return numeroMaximoParcelas;
    }

    public void setNumeroMaximoParcelas(Integer numeroMaximoParcelas) {
        this.numeroMaximoParcelas = numeroMaximoParcelas;
    }
}

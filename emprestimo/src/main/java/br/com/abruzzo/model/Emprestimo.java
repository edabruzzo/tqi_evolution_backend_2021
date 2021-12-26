/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.abruzzo.model;


import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * @author Emmanuel Abruzzo
 * @date 25/12/2021
 */

@Entity
@Table(name = "tb_emprestimo", catalog = "emprestimo", schema = "public")
//@Data //Using @Data for JPA entities is not recommended. It can cause severe performance and memory consumption issues.
public class Emprestimo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Column(name = "valor")
    private Double valor;

    @Column(name = "data_primeira_parcela")
    private Date data_primeira_parcela;

    @Column(name = "numeroMaximoParcelas")
    private Integer numeroMaximoParcelas;

    @Column(name="idCliente")
    private Integer idCliente;

    public Emprestimo() {}

    public Emprestimo(Long id, Double valor, Date data_primeira_parcela, Integer numeroMaximoParcelas, Integer idCliente) {
        this.id = id;
        this.valor = valor;
        this.data_primeira_parcela = data_primeira_parcela;
        this.numeroMaximoParcelas = numeroMaximoParcelas;
        this.idCliente = idCliente;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    @Override
    public String toString() {
        return "Emprestimo{" +
                "id=" + id +
                ", valor=" + valor +
                ", data_primeira_parcela=" + data_primeira_parcela +
                ", numeroMaximoParcelas=" + numeroMaximoParcelas +
                ", idCliente=" + idCliente +
                '}';
    }
}
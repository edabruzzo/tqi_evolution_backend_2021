package br.com.abruzzo.dto;

import javax.persistence.Column;
import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import java.sql.Date;

/**
 * @author Emmanuel Abruzzo
 * @date 31/12/2021
 */
public class EmprestimoDTO {

    private Long id;
    private Double valor;
    private Date data_primeira_parcela;
    private Integer numeroMaximoParcelas;
    private Integer idCliente;


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
}

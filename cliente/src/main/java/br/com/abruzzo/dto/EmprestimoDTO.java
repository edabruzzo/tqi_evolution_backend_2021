package br.com.abruzzo.dto;

import javax.persistence.Column;
import java.sql.Date;

/**
 *
 * Classe Data Transfer Object criada apenas para ter uma representação do Empréstimo
 * Sem criar acoplamento / dependência do microserviço de empréstimo
 *
 * @author Emmanuel Abruzzo
 * @date 26/12/2021
 */
public class EmprestimoDTO {

    private Long id;
    private Double valor;
    private Date data_primeira_parcela;
    private Integer numeroMaximoParcelas;
    private Long idCliente;

    public EmprestimoDTO() {
    }

    public EmprestimoDTO(Long id, Double valor, Date data_primeira_parcela, Integer numeroMaximoParcelas, Long idCliente) {
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

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }
}

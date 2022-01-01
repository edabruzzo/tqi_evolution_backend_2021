package br.com.abruzzo.frontend_cliente_emprestimo.dto;

import java.sql.Date;

/**
 * @author Emmanuel Abruzzo
 * @date 01/01/2022
 */
public class SolicitacaoEmprestimoDTO {

    private Long id;
    private Double valor;
    private Date data_primeira_parcela;
    private Integer numeroMaximoParcelas;
    private Long idCliente;
    private String status;
    private String cpfCliente;
    private String emailCliente;

    public SolicitacaoEmprestimoDTO() { }


    public SolicitacaoEmprestimoDTO(Long id, Double valor, Date data_primeira_parcela, Integer numeroMaximoParcelas, Long idCliente, String status, String cpfCliente, String emailCliente) {
        this.id = id;
        this.valor = valor;
        this.data_primeira_parcela = data_primeira_parcela;
        this.numeroMaximoParcelas = numeroMaximoParcelas;
        this.idCliente = idCliente;
        this.status = status;
        this.cpfCliente = cpfCliente;
        this.emailCliente = emailCliente;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCpfCliente() {
        return cpfCliente;
    }

    public void setCpfCliente(String cpfCliente) {
        this.cpfCliente = cpfCliente;
    }

    public String getEmailCliente() {
        return emailCliente;
    }

    public void setEmailCliente(String emailCliente) {
        this.emailCliente = emailCliente;
    }

    @Override
    public String toString() {
        return "SolicitacaoEmprestimoDTO{" +
                "id=" + id +
                ", valor=" + valor +
                ", data_primeira_parcela=" + data_primeira_parcela +
                ", numeroMaximoParcelas=" + numeroMaximoParcelas +
                ", idCliente=" + idCliente +
                ", status='" + status + '\'' +
                ", cpfCliente='" + cpfCliente + '\'' +
                ", emailCliente='" + emailCliente + '\'' +
                '}';
    }
}

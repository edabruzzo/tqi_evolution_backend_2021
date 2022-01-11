package br.com.abruzzo.tqi_backend_evolution_2021.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDateTime;


/**
 * Classe de entidade JPA que representa um POJO - Empréstimo
 *
 * Validações dos fields usando Bean validation do JAVAEE7
 *
 *
 * @link https://docs.oracle.com/javaee/7/tutorial/bean-validation001.htm
 * @link https://github.com/javaee-samples/javaee7-samples/blob/master/pom.xml
 * @link https://guilhermesteves.dev/tutoriais/regex-uteis-para-o-seu-dia-a-dia/
 * @link https://frontbackend.com/thymeleaf/spring-boot-bootstrap-thymeleaf-datatable
 *
 * @author Emmanuel Abruzzo
 * @date 06/01/2022
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

    @Column(name = "dataPrimeiraParcela")
    @Future //Data Futura
    @JsonFormat(pattern = "dd/MM/yyyy")
    @JsonProperty("dataPrimeiraParcela")
    private Date dataPrimeiraParcela;

    @Column(name="dataSolicitacao")
    @JsonFormat(pattern = "dd/MM/YYYY HH:mm:ss")
    @JsonProperty("dataSolicitacao")
    private LocalDateTime dataSolicitacao;


    @Column(name = "numeroMaximoParcelas")
    @Max(60)
    private Integer numeroParcelas;

    @ManyToOne(fetch = FetchType.LAZY)
    
    @JsonIgnore
    private Cliente cliente;

    @Enumerated(EnumType.STRING)
    private StatusEmprestimo status;


    public Emprestimo() {  }

    public Emprestimo(Long id, Double valor, Date dataPrimeiraParcela, LocalDateTime dataSolicitacao, Integer numeroParcelas, Cliente cliente, StatusEmprestimo status) {
        this.id = id;
        this.valor = valor;
        this.dataPrimeiraParcela = dataPrimeiraParcela;
        this.dataSolicitacao = dataSolicitacao;
        this.numeroParcelas = numeroParcelas;
        this.cliente = cliente;
        this.status = status;
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

    public Date getDataPrimeiraParcela() {
        return dataPrimeiraParcela;
    }

    public void setDataPrimeiraParcela(Date dataPrimeiraParcela) {
        this.dataPrimeiraParcela = dataPrimeiraParcela;
    }

    public LocalDateTime getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(LocalDateTime dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    public Integer getNumeroParcelas() {
        return numeroParcelas;
    }

    public void setNumeroParcelas(Integer numeroParcelas) {
        this.numeroParcelas = numeroParcelas;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public StatusEmprestimo getStatus() {
        return status;
    }

    public void setStatus(StatusEmprestimo status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "Emprestimo{" +
                "id=" + id +
                ", valor=" + valor +
                ", dataPrimeiraParcela=" + dataPrimeiraParcela +
                ", dataSolicitacao=" + dataSolicitacao +
                ", numeroParcelas=" + numeroParcelas +
                ", cliente=" + cliente +
                ", status=" + status +
                '}';
    }
}

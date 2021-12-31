package br.com.abruzzo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 *
 * Classe Data Transfer Object criada apenas para ter uma representação do Empréstimo
 * Sem criar acoplamento / dependência do microserviço de empréstimo
 *
 * @author Emmanuel Abruzzo
 * @date 26/12/2021
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SolicitacaoEmprestimoDTO {

    private Long id;
    private Double valor;
    private Date data_primeira_parcela;
    private Integer numeroMaximoParcelas;
    private Long idCliente;
    private String status;
    private String cpfCliente;
    private String emailCliente;


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

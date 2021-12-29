package br.com.abruzzo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmprestimoDTO {

    private Long id;
    private Double valor;
    private Date data_primeira_parcela;
    private Integer numeroMaximoParcelas;
    private Long idCliente;
    private String status;
    private String cpf;


    @Override
    public String toString() {
        return "EmprestimoDTO{" +
                "id=" + id +
                ", valor=" + valor +
                ", data_primeira_parcela=" + data_primeira_parcela +
                ", numeroMaximoParcelas=" + numeroMaximoParcelas +
                ", idCliente=" + idCliente +
                ", status='" + status + '\'' +
                ", cpf='" + cpf + '\'' +
                '}';
    }
}

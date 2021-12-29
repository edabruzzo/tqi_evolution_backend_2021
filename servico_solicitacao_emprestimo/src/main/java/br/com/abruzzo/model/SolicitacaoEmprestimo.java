package br.com.abruzzo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.sql.Date;

/**
 * @author Emmanuel Abruzzo
 * @date 29/12/2021
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("solicitacaoEmprestimo")
public class SolicitacaoEmprestimo {

    @Id
    private Integer id;

    private Double valor;
    private Date data_primeira_parcela;
    private Integer numeroMaximoParcelas;
    private Long idCliente;
    private String status;
    private String cpfCliente;

    @Override
    public String toString() {
        return "SolicitacaoEmprestimo{" +
                "id=" + id +
                ", valor=" + valor +
                ", data_primeira_parcela=" + data_primeira_parcela +
                ", numeroMaximoParcelas=" + numeroMaximoParcelas +
                ", idCliente=" + idCliente +
                ", status='" + status + '\'' +
                '}';
    }
}

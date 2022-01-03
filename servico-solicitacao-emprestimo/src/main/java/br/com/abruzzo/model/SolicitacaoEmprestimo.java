package br.com.abruzzo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

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

    @Indexed
    @Id
    private Long id;

    private Double valor;

    private Date data_primeira_parcela;

    private Integer numeroMaximoParcelas;

    @Indexed
    private Long idCliente;

    private String status;

    @Indexed
    private String cpfCliente;

    private String emailCliente;


    @Override
    public String toString() {
        return "SolicitacaoEmprestimo{" +
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

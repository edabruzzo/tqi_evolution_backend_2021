package br.com.abruzzo.frontend_cliente_emprestimo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * @author Emmanuel Abruzzo
 * @date 01/01/2022
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

}

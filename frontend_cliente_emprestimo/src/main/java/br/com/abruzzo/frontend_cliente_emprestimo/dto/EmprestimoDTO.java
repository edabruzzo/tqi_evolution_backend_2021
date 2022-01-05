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
public class EmprestimoDTO {

    private Long id;
    private Double valor;
    private Date dataPrimeiraParcela;
    private Integer numeroMaximoParcelas;
    private Integer idCliente;

}

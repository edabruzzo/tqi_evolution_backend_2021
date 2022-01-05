package br.com.abruzzo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * @author Emmanuel Abruzzo
 * @date 02/01/2022
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolicitacaoClienteEmprestimoDTO {


    private String nome;
    private String email;
    private String cpf;
    private String rg;
    private String enderecoCompleto;
    private Double renda;
    private String senha;
    private Double valor;
    private Date dataPrimeiraParcela;
    private Integer numeroMaximoParcelas;



}

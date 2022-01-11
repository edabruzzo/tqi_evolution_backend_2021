package br.com.abruzzo.tqi_backend_evolution_2021.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Emmanuel Abruzzo
 * @date 06/01/2022
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {

    private Long id;
    private String nome;
    private String email;
    private String cpf;
    private String rg;
    private String enderecoCompleto;
    private Double renda;

}

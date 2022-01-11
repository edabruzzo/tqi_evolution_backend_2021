package br.com.abruzzo.tqi_backend_evolution_2021.dto;

import br.com.abruzzo.tqi_backend_evolution_2021.model.Cliente;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDateTime;

/**
 * @author Emmanuel Abruzzo
 * @date 06/01/2022
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmprestimoDTO {


        private Long id;
        private Double valor;
        private Date dataPrimeiraParcela;
        private LocalDateTime dataSolicitacao;
        private Integer numeroMaximoParcelas;
        private Cliente cliente;
        private StatusEmprestimoDTO statusEmprestimoDTO;

    }

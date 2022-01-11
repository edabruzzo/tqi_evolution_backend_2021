package br.com.abruzzo.tqi_backend_evolution_2021.dto;

/**
 * @author Emmanuel Abruzzo
 * @date 06/01/2022
 */
public enum StatusEmprestimoDTO {

    ABERTO,
    EM_PROCESSAMENTO,
    EM_AVALIACAO,
    NAO_AUTORIZADO,
    APROVADO,
    PROBLEMA_AO_SALVAR,
    CONSOLIDADO, NAO_APROVADO;
}

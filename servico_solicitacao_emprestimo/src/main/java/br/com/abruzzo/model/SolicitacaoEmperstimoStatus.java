package br.com.abruzzo.model;

/**
 * @author Emmanuel Abruzzo
 * @date 31/12/2021
 */
public enum SolicitacaoEmprestimoStatus {
    ABERTA,
    EM_PROCESSAMENTO,
    EM_AVALIACAO,
    NAO_AUTORIZADA,
    APROVADA,
    PROBLEMA_AO_SALVAR,
    CONSOLIDADA;
}
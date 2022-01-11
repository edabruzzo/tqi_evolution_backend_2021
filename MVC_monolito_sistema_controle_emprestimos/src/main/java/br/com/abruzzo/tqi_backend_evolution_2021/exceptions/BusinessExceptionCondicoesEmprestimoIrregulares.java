package br.com.abruzzo.tqi_backend_evolution_2021.exceptions;

import org.slf4j.Logger;

/**
 * @author Emmanuel Abruzzo
 * @date 27/12/2021
 */
public class BusinessExceptionCondicoesEmprestimoIrregulares extends AutorizacaoException {
    public BusinessExceptionCondicoesEmprestimoIrregulares(String mensagemErro, Logger logger) {
        super(mensagemErro, logger);
    }
}

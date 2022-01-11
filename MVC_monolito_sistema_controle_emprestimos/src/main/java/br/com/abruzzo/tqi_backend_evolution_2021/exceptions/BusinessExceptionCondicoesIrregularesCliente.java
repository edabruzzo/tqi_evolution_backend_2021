package br.com.abruzzo.tqi_backend_evolution_2021.exceptions;

import org.slf4j.Logger;

/**
 * @author Emmanuel Abruzzo
 * @date 26/12/2021
 */
public class BusinessExceptionCondicoesIrregularesCliente extends AutorizacaoException {
    public BusinessExceptionCondicoesIrregularesCliente(String mensagemErro, Logger logger) {
            super(mensagemErro, logger);
    }
}

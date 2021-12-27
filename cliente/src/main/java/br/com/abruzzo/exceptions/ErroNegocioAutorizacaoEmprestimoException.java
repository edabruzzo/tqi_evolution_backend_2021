package br.com.abruzzo.exceptions;

import org.slf4j.Logger;

/**
 * @author Emmanuel Abruzzo
 * @date 26/12/2021
 */
public class ErroNegocioAutorizacaoEmprestimoException extends Exception {
    public ErroNegocioAutorizacaoEmprestimoException(String localizedMessage, Logger logger) {
        super(localizedMessage);
        logger.error("Erro na criação da solicitação de empréstimo");
        logger.error(localizedMessage);
    }
}

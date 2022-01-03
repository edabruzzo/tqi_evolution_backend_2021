package br.com.abruzzo.exceptions;

import org.slf4j.Logger;

/**
 * @author Emmanuel Abruzzo
 * @date 29/12/2021
 */
public class ErroOperacaoTransacionalBancoException extends InfraBaseException {
    public ErroOperacaoTransacionalBancoException(String localizedMessage, Logger logger) {
        super(localizedMessage, logger);
    }
}

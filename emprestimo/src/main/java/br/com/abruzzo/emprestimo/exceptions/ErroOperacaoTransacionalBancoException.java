package br.com.abruzzo.emprestimo.exceptions;

import org.slf4j.Logger;

/**
 * @author Emmanuel Abruzzo
 * @date 26/12/2021
 */
public class ErroOperacaoTransacionalBancoException extends Exception {
    public ErroOperacaoTransacionalBancoException(String localizedMessage, Logger logger){
        super(localizedMessage);
        logger.error(localizedMessage);
    }
}

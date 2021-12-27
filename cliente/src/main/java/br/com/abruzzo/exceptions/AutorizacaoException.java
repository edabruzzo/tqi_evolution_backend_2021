package br.com.abruzzo.exceptions;

import org.slf4j.Logger;

/**
 * @author Emmanuel Abruzzo
 * @date 27/12/2021
 */
public class AutorizacaoException extends Exception {
    public AutorizacaoException(String message, Logger logger) {
        super(message);
        logger.error(message);
    }
}

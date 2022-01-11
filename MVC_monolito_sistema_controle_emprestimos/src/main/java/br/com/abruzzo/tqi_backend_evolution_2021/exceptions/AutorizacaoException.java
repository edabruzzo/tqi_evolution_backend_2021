package br.com.abruzzo.tqi_backend_evolution_2021.exceptions;

import org.slf4j.Logger;

/**
 * @author Emmanuel Abruzzo
 * @date 27/12/2021
 */
public class AutorizacaoException extends RuntimeException {
    public AutorizacaoException(String message, Logger logger) {
        super(message);
        logger.error(message);
    }
}

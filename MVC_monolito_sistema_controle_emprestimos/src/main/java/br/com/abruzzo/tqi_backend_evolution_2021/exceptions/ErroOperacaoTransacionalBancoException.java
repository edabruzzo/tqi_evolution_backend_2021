package br.com.abruzzo.tqi_backend_evolution_2021.exceptions;

import org.slf4j.Logger;

/**
 * @author Emmanuel Abruzzo
 * @date 06/01/2022
 */
public class ErroOperacaoTransacionalBancoException extends RuntimeException {
    public ErroOperacaoTransacionalBancoException(String mensagemErro, Logger logger) {
        super(mensagemErro);
        logger.error(mensagemErro);
    }
}

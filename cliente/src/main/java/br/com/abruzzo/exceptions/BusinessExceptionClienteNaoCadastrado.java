package br.com.abruzzo.exceptions;

import org.slf4j.Logger;

/**
 * @author Emmanuel Abruzzo
 * @date 26/12/2021
 */
public class BusinessExceptionClienteNaoCadastrado extends Exception {
    public BusinessExceptionClienteNaoCadastrado(String mensagemErro, Logger logger) {
        super(mensagemErro);
        logger.error(mensagemErro);
    }
}

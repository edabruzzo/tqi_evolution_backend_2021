package br.com.abruzzo.exceptions;

import org.slf4j.Logger;

/**
 * @author Emmanuel Abruzzo
 * @date 26/12/2021
 */
public class BusinessExceptionClienteNaoCadastrado extends AutorizacaoException {
    public BusinessExceptionClienteNaoCadastrado(String mensagemErro, Logger logger) {
        super(mensagemErro,logger);
    }
}

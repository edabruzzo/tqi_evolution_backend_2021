package br.com.abruzzo.exceptions;

import org.slf4j.Logger;

/**
 * @author Emmanuel Abruzzo
 * @date 26/12/2021
 */
public class BusinessExceptionCondicoesIrregularesCliente extends Exception {
    public BusinessExceptionCondicoesIrregularesCliente(String mensagemErro, Logger logger) {
            super(mensagemErro);
            logger.error(mensagemErro);
    }
}

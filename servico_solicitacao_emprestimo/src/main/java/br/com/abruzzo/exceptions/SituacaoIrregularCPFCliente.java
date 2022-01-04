package br.com.abruzzo.exceptions;

import org.slf4j.Logger;

/**
 * @author Emmanuel Abruzzo
 * @date 29/12/2021
 */
public class SituacaoIrregularCPFCliente extends Exception {
    public SituacaoIrregularCPFCliente(String mensagemErro, Logger logger) {
            super(mensagemErro);
            logger.error(mensagemErro);
    }
}

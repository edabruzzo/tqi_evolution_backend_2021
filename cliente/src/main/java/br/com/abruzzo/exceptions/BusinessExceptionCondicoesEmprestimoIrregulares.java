package br.com.abruzzo.exceptions;

import org.slf4j.Logger;

/**
 * @author Emmanuel Abruzzo
 * @date 27/12/2021
 */
public class BusinessExceptionCondicoesEmprestimoIrregulares extends Exception {
    public BusinessExceptionCondicoesEmprestimoIrregulares(String mensagemErro, Logger logger) {
        super(mensagemErro);
        logger.error(mensagemErro);
    }
}

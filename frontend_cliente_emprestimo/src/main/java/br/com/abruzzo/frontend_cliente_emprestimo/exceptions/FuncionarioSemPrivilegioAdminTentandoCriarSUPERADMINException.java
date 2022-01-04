package br.com.abruzzo.frontend_cliente_emprestimo.exceptions;

import org.slf4j.Logger;

/**
 * @author Emmanuel Abruzzo
 * @date 04/01/2022
 */
public class FuncionarioSemPrivilegioAdminTentandoCriarSUPERADMINException extends RuntimeException {
    public FuncionarioSemPrivilegioAdminTentandoCriarSUPERADMINException(String mensagemErro, Logger logger) {
            super(mensagemErro);
            logger.error(mensagemErro);
    }
}

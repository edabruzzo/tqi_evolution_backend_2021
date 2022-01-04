package br.com.abruzzo.exceptions;

import org.slf4j.Logger;

/**
 * @author Emmanuel Abruzzo
 * @date 03/01/2022
 */
public class UsuarioSemPrivilegioAdminTentandoSalvarAdminException extends RuntimeException {
    public UsuarioSemPrivilegioAdminTentandoSalvarAdminException(String mensagemErro, Logger logger) {
            super(mensagemErro);
            logger.error(mensagemErro);
    }
}

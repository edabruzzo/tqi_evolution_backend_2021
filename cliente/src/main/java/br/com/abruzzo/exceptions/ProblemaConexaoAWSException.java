package br.com.abruzzo.exceptions;

/**
 * @author Emmanuel Abruzzo
 * @date 25/12/2021
 */
public class ProblemaConexaoAWSException extends Exception {
    public ProblemaConexaoAWSException(String localizedMessage) {
        super(localizedMessage);
    }
}

package br.com.abruzzo.config;

/**
 * @author Emmanuel Abruzzo
 * @date 29/12/2021
 */
public enum ParametrosConfig {

    ENDPOINT_BASE("http://servico_solicitacao_emprestimo"),
    SERVICO_EMPRESTIMO("/servico_emprestimo");

    private final String value;

    ParametrosConfig(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}


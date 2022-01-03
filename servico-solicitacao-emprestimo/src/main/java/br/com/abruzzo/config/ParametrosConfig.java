package br.com.abruzzo.config;

/**
 * @author Emmanuel Abruzzo
 * @date 29/12/2021
 */
public enum ParametrosConfig {

    ENDPOINT_BASE("http://servico-solicitacao-emprestimo"),
    SERVICO_EMPRESTIMO("http://servico-emprestimo"),
    OPERACAO_EMPRESTIMO_ENDPOINT(SERVICO_EMPRESTIMO.getValue().concat("/emprestimo"));

    private final String value;

    ParametrosConfig(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}


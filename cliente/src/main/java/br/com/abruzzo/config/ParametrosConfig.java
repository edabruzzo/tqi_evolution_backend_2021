package br.com.abruzzo.config;

public enum ParametrosConfig {

    ENDPOINT_BASE("http://servico-cliente"),
    CLIENTE_ENDPOINT("/cliente"),
    OPERACAO_EMPRESTIMO_ENDPOINT("http://servico-solicitacao-emprestimo/solicitacao_emprestimo"),
    SERVICO_SOLICITACAO_EMPRESTIMO("servico-solicitacao-emprestimo"),
    PATH_SOLICITACAO_EMPRESTIMO("solicitacao_emprestimo");

    private final String value;

    ParametrosConfig(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}

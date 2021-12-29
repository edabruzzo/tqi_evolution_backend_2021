package br.com.abruzzo.config;

public enum ParametrosConfig {

    ENDPOINT_BASE("http://servico_cliente"),
    CLIENTE_ENDPOINT("/cliente"),
    TABLENAME("tb_cliente"),
    OPERACAO_EMPRESTIMO_ENDPOINT("http://servico_solicitacao_emprestimo/solicitar_emprestimo"),
    SERVICO_SOLICITACAO_EMPRESTIMO("servico_solicitacao_emprestimo");

    private final String value;

    ParametrosConfig(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}

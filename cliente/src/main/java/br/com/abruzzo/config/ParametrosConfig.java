package br.com.abruzzo.config;

public enum ParametrosConfig {

    ENDPOINT_BASE("http://servico_cliente"),
    CLIENTE_ENDPOINT("/cliente"),
    TABLENAME("tb_cliente"),
    OPERACAO_EMPRESTIMO_ENDPOINT("http://servico_emprestimo/emprestimo/");

    private final String value;

    ParametrosConfig(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}

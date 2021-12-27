package br.com.abruzzo.config;

public enum ParametrosConfig {

    ENDPOINT_BASE("http://localhost:8080"),
    CLIENTE_ENDPOINT("/cliente"),
    TABLENAME("tb_cliente"),
    OPERACAO_EMPRESTIMO_ENDPOINT("http://localhost:8081/emprestimo/");

    private final String value;

    ParametrosConfig(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}

package br.com.abruzzo.config;

public enum ParametrosConfig {

    ENDPOINT_BASE("https://localhost:8080"),
    CLIENTE_ENDPOINT("/cliente"),
    TABLENAME("tb_cliente"),
    OPERACAO_EMPRESTIMO_ENDPOINT("https://localhost:8081/emprestimo");

    private final String value;

    ParametrosConfig(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}

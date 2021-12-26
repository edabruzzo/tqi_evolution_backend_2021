package br.com.abruzzo.emprestimo.config;

public enum ParametrosConfig {

    ENDPOINT_BASE("https://localhost:8080"),
    EMPRESTIMO_ENDPOINT("/emprestimo"),
    TABLENAME("tb_emprestimo");

    private final String value;

    ParametrosConfig(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}

package br.com.abruzzo.config;

public enum ParametrosConfig {

    ENDPOINT_BASE("http://localhost:8081"),
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

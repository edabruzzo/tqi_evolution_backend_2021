package br.com.abruzzo.config;

public enum ParametrosConfig {

    ENDPOINT_BASE("http://servico_emprestimo"),
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

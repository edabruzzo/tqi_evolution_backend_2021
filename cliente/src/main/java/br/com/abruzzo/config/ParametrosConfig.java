package br.com.abruzzo.config;

public enum ParametrosConfig {

    ENDPOINT_BASE("https://localhost:8080"),
    CLIENTE_ENDPOINT("/cliente"),
    ENDPOINT_DYNAMO("http://localhost:8000"),
    REGION_DYNAMO("us-east-2"),
    TABLENAME("tb_clientes");

    private final String value;

    ParametrosConfig(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}

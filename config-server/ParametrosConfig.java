package br.com.abruzzo.WebFluxReactiveAPI.config;

public enum ParametrosConfig {

    ENDPOINT_BASE("https://localhost:8080"),
    HEROES_ENDPOINT("/heroes"),
    ENDPOINT_DYNAMO("http://localhost:8000"),
    REGION_DYNAMO("us-east-1"),
    TABLENAME("tb_heroes");

    private final String value;

    ParametrosConfig(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}

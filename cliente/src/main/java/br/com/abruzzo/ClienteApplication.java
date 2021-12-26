package br.com.abruzzo;


import br.com.abruzzo.exceptions.ProblemaCriacaoTabelaException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClienteApplication {

    public static void main(String[] args) throws ProblemaCriacaoTabelaException {

        //ClienteTableConfig.criaSchemaTabelas();
        SpringApplication.run(ClienteApplication.class, args);
    }

}

package br.com.abruzzo.config;

import java.util.Arrays;

import br.com.abruzzo.exceptions.ProblemaCriacaoTabelaException;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;



public class ClienteTableConfig {


    private ClienteTableConfig() {
        throw new IllegalStateException("Classe de utilidade que não deve ser instanciada");
    }


    public static void criaSchemaTabelas() {

        DynamoDB dynamoDB = ConnectionFactory.obterDynamoDB();
        String tableName = ParametrosConfig.TABLENAME.getValue();

        try{
            Table table = dynamoDB.getTable(tableName);
            table.delete();
            System.out.println("Tabela deletada");
        }catch(Exception exception){

            try {
                throw new ProblemaCriacaoTabelaException(exception.getLocalizedMessage());
            } catch (ProblemaCriacaoTabelaException e) {
                e.printStackTrace();
            }

            System.out.println(exception.getLocalizedMessage());
        }


        try {
            System.out.println("Creating table..."+tableName);
            Table table = dynamoDB.createTable(tableName,
                    Arrays.asList(new KeySchemaElement("id", KeyType.HASH)),
                    Arrays.asList(new AttributeDefinition("id", ScalarAttributeType.S)),
                    new ProvisionedThroughput(5L, 5L));
            table.waitForActive();
            System.out.println("Successo " + table.getDescription().getTableStatus());

        }
        catch (Exception e) {
            try {
                throw new ProblemaCriacaoTabelaException(e.getLocalizedMessage());
            } catch (ProblemaCriacaoTabelaException ex) {
                ex.printStackTrace();
            }
            System.err.println("Não foi possível criar a tabela");
            System.err.println(e.getMessage());
        }

    }

}


package br.com.abruzzo.WebFluxReactiveAPI.config;

import java.util.Arrays;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;



public class HeroesTableConfig {

    public static void criaSchemaTabelas() throws Exception {

        DynamoDB dynamoDB = ConnectionFactory.obterDynamoDB();
        String tableName = ParametrosConfig.TABLENAME.getValue();

        try{
            Table table = dynamoDB.getTable(tableName);
            table.delete();
            System.out.println("Tabela deletada");
        }catch(Exception exception){
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
            System.err.println("Não foi possível criar a tabela");
            System.err.println(e.getMessage());
        }

    }

}


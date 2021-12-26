package br.com.abruzzo.config;

import java.util.Arrays;

import br.com.abruzzo.exceptions.ProblemaCriacaoTabelaException;
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



public class ClienteTableConfig {


    private ClienteTableConfig() {
        throw new IllegalStateException("Classe de utilidade que não deve ser instanciada");
    }




    public static void criaSchemaTabelas() throws ProblemaCriacaoTabelaException {

        DynamoDB dynamoDB = ConnectionFactory.obterDynamoDB();
        String tableName = ParametrosConfig.TABLENAME.getValue();

        try{
            Table table = dynamoDB.getTable(tableName);
            if(table != null) table.delete();
            System.out.println("Tabela deletada");
        }catch(Exception exception){
                throw new ProblemaCriacaoTabelaException(exception.getLocalizedMessage());
        }


        try {
            System.out.println("Criando a tabela..."+tableName);
            Table table = dynamoDB.createTable(tableName,
                    Arrays.asList(new KeySchemaElement("id", KeyType.HASH)),
                    Arrays.asList(new AttributeDefinition("id", ScalarAttributeType.S)),
                    new ProvisionedThroughput(5L, 5L));
            table.waitForActive();
            System.out.println("Successo " + table.getDescription().getTableStatus());

        }
        catch (Exception e) {
            System.err.println("Não foi possível criar a tabela");
            throw new ProblemaCriacaoTabelaException(e.getLocalizedMessage());
        }

    }




        public static void main(String[] args) throws Exception {

            AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                    .withEndpointConfiguration(new AwsClientBuilder
                            .EndpointConfiguration(ParametrosConfig.ENDPOINT_DYNAMO.getValue(),
                            ParametrosConfig.REGION_DYNAMO.getValue()))
                    .build();

            DynamoDB dynamoDB = new DynamoDB(client);

            String tableName = ParametrosConfig.TABLENAME.getValue();

            try {
                System.out.println("Tentativa de criar a tabela...");
                Table table = dynamoDB.createTable(tableName,
                                Arrays.asList(new KeySchemaElement("id", KeyType.HASH)),
                                Arrays.asList(new AttributeDefinition("id", ScalarAttributeType.S)),
                                new ProvisionedThroughput(5L, 5L));
                table.waitForActive();
                System.out.println("Tabela criada com successo -> status -> " + table.getDescription().getTableStatus());


            }
            catch (Exception e) {
                System.err.println("Não foi possível criar a tabela: ");
                System.err.println(e.getMessage());
            }

        }
    }


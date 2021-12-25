package br.com.abruzzo.config;

import br.com.abruzzo.exceptions.ProblemaConexaoAWSException;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;

public class ConnectionFactory {


    public static DynamoDB obterDynamoDB() {

        DynamoDB dynamoDB = null;
        try {
            AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                    .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
                            ParametrosConfig.ENDPOINT_DYNAMO.getValue(),
                            ParametrosConfig.REGION_DYNAMO.getValue()))
                    .build();
            dynamoDB = new DynamoDB(client);

        }catch(Exception erro){

            try {
                throw new ProblemaConexaoAWSException(erro.getLocalizedMessage());
            } catch (ProblemaConexaoAWSException e) {
                e.printStackTrace();
            }

        }

        return dynamoDB;
    }
}

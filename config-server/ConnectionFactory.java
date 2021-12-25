package br.com.abruzzo.WebFluxReactiveAPI.config;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;

public class ConnectionFactory {


    public static DynamoDB obterDynamoDB() {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
                        ParametrosConfig.ENDPOINT_DYNAMO.getValue(),
                        ParametrosConfig.REGION_DYNAMO.getValue()))
                .build();
        DynamoDB dynamoDB = new DynamoDB(client);

        return dynamoDB;
    }
}

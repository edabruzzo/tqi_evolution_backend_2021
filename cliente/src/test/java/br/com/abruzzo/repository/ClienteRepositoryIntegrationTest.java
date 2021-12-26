package br.com.abruzzo.repository;

import br.com.abruzzo.ClienteApplication;
import br.com.abruzzo.model.Cliente;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import reactor.core.publisher.Flux;


import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


/**
 *
 * @link https://www.baeldung.com/spring-data-dynamodb
 * @author Emmanuel Abruzzo
 * @date 26/12/2021
 */


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ClienteApplication.class)
@WebAppConfiguration
@ActiveProfiles("dynamodb")
@TestPropertySource(properties = {
        "amazon.dynamodb.endpoint=http://localhost:8000/",
        "amazon.aws.accesskey=test1",
        "amazon.aws.secretkey=test231" })
public class ClienteRepositoryIntegrationTest {


    private DynamoDBMapper dynamoDBMapper;

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    @Autowired
    ClienteRepository repository;

    private static final String EXPECTED_NAME = "Andrea";
    private static final String EXPECTED_EMAIL = "andrea@gmail.com";

    private Cliente cliente1  = new Cliente("1","Andrea","andrea@gmail.com","11111111111","11111111-1","Rua 1",10000d,"123");


    @Before
    public void setup() throws Exception {
        dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);

        CreateTableRequest tableRequest = dynamoDBMapper
                .generateCreateTableRequest(Cliente.class);
        tableRequest.setProvisionedThroughput(
                new ProvisionedThroughput(1L, 1L));
        amazonDynamoDB.createTable(tableRequest);

        dynamoDBMapper.batchDelete((Flux<Cliente>)repository.findAll());

    }

    @Test
    public void givenClientWithExpectedName_whenRunFindAll_thenClientIsFound() {
        repository.save(this.cliente1);
        Optional<Cliente> resultado = (Optional<Cliente>) repository.findById(cliente1.getId());
        assertThat(resultado.get().getNome(), is(equals(this.cliente1.getNome())));
    }
}

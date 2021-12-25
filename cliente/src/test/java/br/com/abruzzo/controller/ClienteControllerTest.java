package br.com.abruzzo.controller;

import br.com.abruzzo.config.ParametrosConfig;
import br.com.abruzzo.model.Cliente;
import br.com.abruzzo.service.ClienteService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author Emmanuel Abruzzo
 * @date 25/12/2021
 */

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = ClienteController.class)
@Import(ClienteService.class)
class ClienteControllerTest {

    @Mock
    private RestTemplate restTemplate;

    @MockBean
    ClienteService clienteService;

    @Autowired
    WebTestClient webTestClient;

    private final String URI_CLIENTE = ParametrosConfig.ENDPOINT_BASE.getValue()
                                 .concat(ParametrosConfig.CLIENTE_ENDPOINT.getValue());


    private Logger logger = LoggerFactory.getLogger(ClienteControllerTest.class);

    private Cliente cliente1  = new Cliente("1","Andrea","andrea@gmail.com","11111111111","11111111-1","Rua 1",10000d,"123");
    private Cliente cliente2 = new Cliente("2","José","jose@gmail.com","22222222222","22222222-2","Rua 2",5000d,"123456");
    private Cliente cliente3 = new Cliente("3","Alberto","alberto@gmail.com","33333333333","33333333-3","Rua 3",15000d,"333333");
    private Cliente cliente4 = new Cliente("4","Maria","maria@gmail.com","44444444444","44444444-4","Rua 2",8500d,"321654987");


    @InjectMocks
    private ClienteController clienteController;

    MockMvc mockMvc;

    @Before
    public void setUp() {

        System.out.println("@Beforeeach is called!");
        MockitoAnnotations.initMocks(this);

        this.mockMvc = MockMvcBuilders.standaloneSetup(clienteController)
                .alwaysExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .build();
    }


    @Test
    void whenCallPOSTRequest_saveNewClientOnDatabase(){

        Mockito.when(clienteService.save(cliente1)).thenReturn(Mono.just(cliente1));

        webTestClient.post()
                .uri(URI_CLIENTE)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(cliente1))
                .exchange()
                .expectStatus().isCreated();

        Mockito.verify(clienteService, Mockito.times(1)).save(cliente1);


    }


    @Test
    void whenCallGETMethod_findAllClients() {

        Mockito.when(clienteService.findAll()).thenReturn(Flux.just(cliente1, cliente2, cliente3,cliente4));

        webTestClient.get()
                .uri(URI_CLIENTE)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Cliente.class);

        Mockito.verify(clienteService, Mockito.times(1)).findAll();
    }


    @Test
    void whenCallGETMethodById_findOneClient() {

        Mockito.when(restTemplate.getForEntity((this.URI_CLIENTE + "/" + cliente2.getId()),
                        Mono.class))
                .thenReturn(new ResponseEntity(cliente2, HttpStatus.OK));
        assertThat(cliente2.getNome()).isEqualTo("José");

    }



    @Test
    public void testFooDelete() throws Exception {

        ClienteService spy = Mockito.spy(clienteService);
        Mockito.doNothing().when(spy).deleteById("1");

        webTestClient.delete()
                .uri("/cliente/1")
                .exchange()
                .expectStatus().isOk();
    }


    @Test
    void whenCallUpdateById_AssertThatClienteisUpdated(){

            ClienteService spy = Mockito.spy(clienteService);
            Mockito.when(spy.save(cliente1)).thenReturn(Mono.just(cliente1));
            this.webTestClient.put()
                    .uri(this.URI_CLIENTE+"/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(this.cliente1)
                    .exchange()
                    .expectStatus().is5xxServerError();

        }




}
package br.com.abruzzo.controller;

import br.com.abruzzo.config.ParametrosConfig;
import br.com.abruzzo.model.Cliente;
import br.com.abruzzo.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * @author Emmanuel Abruzzo
 * @date 25/12/2021
 */

@ExtendWith(SpringExtension.class)
@WebMvcTest(ClienteController.class)
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

    private Cliente cliente1  = new Cliente(1L,"Andrea","andrea@gmail.com","11111111111","11111111-1","Rua 1",10000d,"123");
    private Cliente cliente2 = new Cliente(2L,"José","jose@gmail.com","22222222222","22222222-2","Rua 2",5000d,"123456");
    private Cliente cliente3 = new Cliente(3L,"Alberto","alberto@gmail.com","33333333333","33333333-3","Rua 3",15000d,"333333");
    private Cliente cliente4 = new Cliente(4L,"Maria","maria@gmail.com","44444444444","44444444-4","Rua 2",8500d,"321654987");


    @InjectMocks
    private ClienteController clienteController;

    @Autowired
    MockMvc mockMvc;

    @Before
    public void setUp() {

        System.out.println("@Beforeeach is called!");
        MockitoAnnotations.initMocks(this);

        this.mockMvc = MockMvcBuilders.standaloneSetup(clienteController)
                .alwaysExpect(content().contentType("application/json;charset=UTF-8"))
                .build();
    }


    @Test
    void whenCallPOSTRequest_saveNewClientOnDatabase() throws Exception {

        Mockito.when(clienteService.save(Mockito.any(Cliente.class))).thenReturn(this.cliente1);

        mockMvc.perform(MockMvcRequestBuilders.post("/cliente/")
                        .content(asJsonString(this.cliente1))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));


    }


    @Test
    void whenCallGETMethod_findAllClients() {


        List<Cliente> listaClientes = new ArrayList<>();
        listaClientes.add(this.cliente1);
        listaClientes.add(this.cliente2);
        listaClientes.add(this.cliente3);
        listaClientes.add(this.cliente4);

        Mockito.when(restTemplate.getForEntity("/cliente", List.class))
                .thenReturn(new ResponseEntity(listaClientes, HttpStatus.OK));

        assertThat(listaClientes.size()).isEqualTo(4);
    }


    @Test
    void whenCallGETMethodById_findOneClient() {

        webTestClient.get()
                .uri("/cliente/2")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Cliente.class);

        Mockito.verify(clienteService, Mockito.times(1)).findById(2L);

    }



    @Test
    public void testDelete() throws Exception {

        ClienteService spy = Mockito.spy(clienteService);
        Mockito.doNothing().when(spy).deleteById(1L);

        webTestClient.delete()
                .uri("/cliente/1")
                .exchange()
                .expectStatus().isOk();
    }


    @Test
    void whenCallUpdateById_AssertThatClienteisUpdated() throws Exception {

        Mockito.when(clienteService.save(Mockito.any(Cliente.class))).thenReturn(this.cliente1);

        mockMvc.perform(MockMvcRequestBuilders.post("/cliente/1")
                        .content(asJsonString(this.cliente1))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }



    }




}
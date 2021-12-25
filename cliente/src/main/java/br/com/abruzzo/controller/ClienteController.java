package br.com.abruzzo.controller;


import br.com.abruzzo.config.ParametrosConfig;
import br.com.abruzzo.model.Cliente;
import br.com.abruzzo.service.ClienteService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


/**
 * Rest controller que expõe os recursos da API
 * @author Emmanuel Abruzzo
 * @date 25/12/2021
 */
@RestController
@Slf4j
@RequestMapping("/cliente")
public class ClienteController {

    private static final Logger logger = LoggerFactory.getLogger(ClienteController.class);

    @Autowired
    private ClienteService clienteService;


    /**
     *  Método que retorna um cliente conforme id especificado,
     *  após um GET request via chamada Rest
     * @param id  Id do cliente cadastrado em banco
     * @return    retorna um Cliente no formato JSON
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Mono<Cliente>> getClienteById(@PathVariable String id) {

        logger.info("Chegou GET request no Endpoint {}/{}/{}",
                  ParametrosConfig.ENDPOINT_BASE.getValue()
                , ParametrosConfig.CLIENTE_ENDPOINT.getValue(),id);

        Mono<Cliente> cliente = clienteService.findById(id);
        HttpStatus status = (cliente != null) ? HttpStatus.OK : HttpStatus.NOT_FOUND;

        return new ResponseEntity<Mono<Cliente>>(cliente,status);
    }



    @GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(method = RequestMethod.GET)
    public Flux<Cliente> retornaTodosClientes(){
        logger.info("Chegou GET request no Endpoint {}/{}", ParametrosConfig.ENDPOINT_BASE.getValue()
                , ParametrosConfig.CLIENTE_ENDPOINT.getValue());
        return clienteService.findAll();
    }


}

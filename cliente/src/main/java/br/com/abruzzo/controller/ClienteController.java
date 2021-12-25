package br.com.abruzzo.controller;


import br.com.abruzzo.config.ParametrosConfig;
import br.com.abruzzo.model.Cliente;
import br.com.abruzzo.service.ClienteService;
import br.com.abruzzo.service.clienteService;
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


    /**
     *  Método que retorna um Flux de todos os clientes cadastrados na base
     *  após um GET request via chamada Rest
     * @param id  Id do cliente cadastrado em banco
     * @return    retorna um Flux stream de clientes no formato JSON
     */
    @GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(method = RequestMethod.GET)
    public Flux<Cliente> retornaTodosClientes(){
        logger.info("Chegou GET request no Endpoint {}/{}", ParametrosConfig.ENDPOINT_BASE.getValue()
                , ParametrosConfig.CLIENTE_ENDPOINT.getValue());
        return clienteService.findAll();
    }



    /**
     *  Método responsável por cadastrar um cliente na base de dados
     *  após uma request via chamada Rest utilizando o método HTTP POST
     *
     *  @param id  Id do cliente cadastrado em banco
     * @return    retorna uma Mono stream com o cliente salvo no formato JSON
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Cliente> createCliente(@RequestBody Cliente cliente){
        logger.info("Requisição para salvar um cliente na base", cliente.toString());
        logger.info("{}", cliente.toString());
        logger.info("POST request received on endpoint: {}", ParametrosConfig.ENDPOINT_BASE.getValue());
        Mono<Cliente> clienteSalvo = null;
        try{
            clienteSalvo = clienteService.save(cliente);
        }catch(Exception erro){
            logger.debug(erro.getLocalizedMessage());
        }
        logger.info("Cliente {} was saved", cliente.toString());
        logger.info("{}", cliente);
        return clienteSalvo;
    }


    /**
     *  Método responsável por atualizar um cliente na base de dados
     *  após uma request via chamada Rest utilizando o método HTTP PUT
     * @param id  Id do cliente cadastrado em banco
     * @return    retorna uma Mono stream com o cliente salvo no formato JSON
     */

    @PutMapping(value = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Mono<ResponseEntity<Cliente>> atualizaCliente(@PathVariable String id,
                                                 @RequestBody Cliente clienteUpdated) {
        clienteUpdated.setId(id);
        Mono<ResponseEntity<Cliente>> resposta = Mono.just(ResponseEntity.notFound().build());
        try {
            resposta = clienteService.findById(id)
                    .flatMap(oldCliente -> {
                        oldCliente.setId(clienteUpdated.getId());
                        oldCliente.setUniverse(clienteUpdated.getUniverse());
                        oldCliente.setName(clienteUpdated.getName());
                        oldCliente.setCountFilms(clienteUpdated.getCountFilms());
                        return clienteService.save(oldCliente);
                    }).map(Cliente -> ResponseEntity.ok(Cliente))
                    .defaultIfEmpty(ResponseEntity.notFound().build());
        }catch(Exception erro){
            logger.debug(erro.getLocalizedMessage());
        }
        return resposta;

    }


    @DeleteMapping(value="{id}")
    @ResponseStatus(code=HttpStatus.OK)
    public void delete(@PathVariable String id){
        logger.info("DELETE request received on endpoint: {}", ParametrosConfig.ENDPOINT_BASE.getValue());
        try{
            clienteService.deleteById(id);
            logger.info("Cliente with id {} was deleted", id);
        }catch (Exception erro){
            logger.debug(erro.getLocalizedMessage());
        }
    }
    
    


}

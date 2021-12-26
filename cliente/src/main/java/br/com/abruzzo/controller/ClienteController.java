package br.com.abruzzo.controller;


import br.com.abruzzo.config.ParametrosConfig;
import br.com.abruzzo.exceptions.ErroOperacaoTransacionalBancoException;
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


import java.util.List;
import java.util.Optional;


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
    public ResponseEntity<Cliente> getClienteById(@PathVariable Long id) {

        logger.info("Chegou GET request no Endpoint {}/{}/{}",
                  ParametrosConfig.ENDPOINT_BASE.getValue()
                , ParametrosConfig.CLIENTE_ENDPOINT.getValue(),id);

        Optional<Cliente> cliente = clienteService.findById(id);

        if (cliente.isPresent()) return ResponseEntity.ok().body(cliente.get());
        else return (ResponseEntity) ResponseEntity.notFound().build();

    }


    /**
     *  Método que retorna um Flux de todos os clientes cadastrados na base
     *  após um GET request via chamada Rest
     *
     * @return    retorna um Flux stream de clientes no formato JSON
     */
    @GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<Cliente> retornaTodosClientes(){
        logger.info("Chegou GET request no Endpoint {}/{}", ParametrosConfig.ENDPOINT_BASE.getValue()
                , ParametrosConfig.CLIENTE_ENDPOINT.getValue());
        return clienteService.findAll();
    }



    /**
     *  Método responsável por cadastrar um cliente na base de dados
     *  após uma request via chamada Rest utilizando o método HTTP POST
     *
     * @param cliente Objeto JSON cliente que chega como payload no request body
     * @return    retorna uma Mono stream com o cliente salvo no formato JSON
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente createCliente(@RequestBody Cliente cliente) throws ErroOperacaoTransacionalBancoException {

        logger.info("Requisição para salvar um cliente na base");
        logger.info("POST recebido no seguinte endpoint: {}", ParametrosConfig.ENDPOINT_BASE.getValue());

        Cliente clienteSalvo = null;
        try{
            clienteSalvo = clienteService.save(cliente);
        }catch(Exception erro){

            throw new ErroOperacaoTransacionalBancoException(erro.getLocalizedMessage(), logger);

        }
        logger.info("Cliente {} foi salvo", cliente);
        logger.info("{}", cliente);
        return clienteSalvo;
    }


    /**
     *  Método responsável por atualizar um cliente na base de dados
     *  após uma request via chamada Rest utilizando o método HTTP PUT
     *
     * @param id  Id do cliente cadastrado em banco
     * @param clienteUpdated cliente Objeto JSON cliente que chega como payload no request body
     * @return    retorna uma Mono stream com o cliente salvo no formato JSON
     */
    @PutMapping(value = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Cliente> atualizaCliente(@PathVariable Long id,
                                                         @RequestBody Cliente clienteUpdated) throws ErroOperacaoTransacionalBancoException {

        logger.info("Requisição para atualizar um cliente na base");
        logger.info("PUT request recebido no seguinte endpoint: {}", ParametrosConfig.ENDPOINT_BASE.getValue());
        clienteUpdated.setId(id);
        ResponseEntity<Cliente> resposta = null;
        try {
            clienteService.save(clienteUpdated);
        }catch(Exception erro){
            resposta = ResponseEntity.notFound().build();
            throw new ErroOperacaoTransacionalBancoException(erro.getLocalizedMessage(), logger);
        }
        return resposta;

    }



    /**
     *  Método responsável por receber o request para deletar um cliente na base de dados
     *  após uma request via chamada Rest utilizando o método HTTP DELETE
     *
     * @param id  Id do cliente cadastrado em banco
     * @return    retorna uma Mono stream com o cliente salvo no formato JSON
     */
    @DeleteMapping(value="{id}")
    @ResponseStatus(code=HttpStatus.OK)
    public void delete(@PathVariable Long id) throws ErroOperacaoTransacionalBancoException {
        logger.info("DELETE request recebido no endpoint: {}", ParametrosConfig.ENDPOINT_BASE.getValue());
        try{
            clienteService.deleteById(id);
            logger.info("Cliente com id {} foi deletado", id);
        }catch (Exception erro){
            throw new ErroOperacaoTransacionalBancoException(erro.getLocalizedMessage(), logger);
        }
    }
    

}

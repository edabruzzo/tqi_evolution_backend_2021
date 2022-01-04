package br.com.abruzzo.controller;


import br.com.abruzzo.config.ParametrosConfig;
import br.com.abruzzo.dto.ClienteDTO;
import br.com.abruzzo.exceptions.ErroOperacaoTransacionalBancoException;
import br.com.abruzzo.model.Cliente;
import br.com.abruzzo.service.ClienteService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
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

    @Autowired
    private ModelMapper modelMapper;


    /**
     *  Método que retorna um cliente conforme id especificado,
     *  após um GET request via chamada Rest
     * @param id  Id do cliente cadastrado em banco
     * @return    retorna um ClienteDTO - Data Transfer Object no formato JSON
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed({"FUNCIONARIO","SUPER_ADMIN"})
    public ClienteDTO getClienteById(@PathVariable Long id) {

        logger.info("Chegou GET request no Endpoint {}/{}/{}",
                  ParametrosConfig.ENDPOINT_BASE.getValue()
                , ParametrosConfig.CLIENTE_ENDPOINT.getValue(),id);

        Optional<Cliente> cliente = clienteService.findById(id);

        if (cliente.isPresent()){
            ClienteDTO clienteDTO = this.modelMapper.map(cliente.get(), ClienteDTO.class);
            return clienteDTO;
        }
        else
            return new ClienteDTO();

    }


    /**
     *  Método que retorna um Flux de todos os clientes cadastrados na base
     *  após um GET request via chamada Rest
     *
     * @return    retorna uma lista de clientes DTO no formato JSON
     */
    @GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed({"FUNCIONARIO","SUPER_ADMIN"})
    public List<ClienteDTO> retornaTodosClientes(){

        logger.info("Chegou GET request no Endpoint {}/{}", ParametrosConfig.ENDPOINT_BASE.getValue()
                , ParametrosConfig.CLIENTE_ENDPOINT.getValue());

        List<ClienteDTO> listaClientesDTO = new ArrayList<>();


        List<Cliente> listaClientes =  clienteService.findAll();

        listaClientes.stream().forEach(cliente -> {
            ClienteDTO clienteDTO = this.modelMapper.map(cliente, ClienteDTO.class);
            listaClientesDTO.add(clienteDTO);
        });

        return listaClientesDTO;

    }



    /**
     *  Método responsável por cadastrar um cliente na base de dados
     *  após uma request via chamada Rest utilizando o método HTTP POST
     *
     * @param clienteDTO cliente DTO - Data Transfer Object - JSON cliente que chega como payload no request body
     * @return    retorna um  cliente DTO salvo no formato JSON
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @RolesAllowed({"FUNCIONARIO","SUPER_ADMIN"})
    public ClienteDTO criaNovoCliente(@RequestBody Cliente clienteDTO) throws ErroOperacaoTransacionalBancoException {

        logger.info("Requisição para salvar um cliente na base");
        logger.info("POST recebido no seguinte endpoint: {}", ParametrosConfig.ENDPOINT_BASE.getValue());

        Cliente clienteSalvo = null;
        Cliente cliente = this.modelMapper.map(clienteDTO, Cliente.class);
        try{
            clienteSalvo = clienteService.save(cliente);
        }catch(Exception erro){

            throw new ErroOperacaoTransacionalBancoException(erro.getLocalizedMessage(), logger);

        }

        ClienteDTO clienteDTOSalvo = this.modelMapper.map(clienteSalvo, ClienteDTO.class);

        logger.info("Cliente {} foi salvo", clienteDTOSalvo);
        logger.info("{}", clienteDTOSalvo);
        return clienteDTOSalvo;
    }


    /**
     *  Método responsável por atualizar um cliente na base de dados
     *  após uma request via chamada Rest utilizando o método HTTP PUT
     *
     * @param id  Id do cliente cadastrado em banco
     * @param clienteUpdatedDTO cliente DTO - Data Transfer Object - JSON cliente que chega como payload no request body
     * @return    retorna uma Mono stream com o cliente salvo no formato JSON
     */
    @PutMapping(value = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @RolesAllowed({"FUNCIONARIO","SUPER_ADMIN"})
    public ResponseEntity<ClienteDTO> atualizaCliente(@PathVariable Long id,
                                                         @RequestBody ClienteDTO clienteUpdatedDTO) throws ErroOperacaoTransacionalBancoException {

        logger.info("Requisição para atualizar um cliente na base");
        logger.info("PUT request recebido no seguinte endpoint: {}", ParametrosConfig.ENDPOINT_BASE.getValue());
        clienteUpdatedDTO.setId(id);
        ResponseEntity<ClienteDTO> resposta = null;
        try {
            Cliente clienteUpdated = this.modelMapper.map(clienteUpdatedDTO,Cliente.class);
            Cliente clienteSalvo = clienteService.save(clienteUpdated);
            ClienteDTO clienteDTO = this.modelMapper.map(clienteSalvo, ClienteDTO.class);
            resposta = ResponseEntity.ok().body(clienteDTO);
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
     *
     *  Segurança: Apenas usuário com a Role SUPER_ADMIN tem autorização para invocar este
     *  método do Controller
     *
     *
     * @param id  Id do cliente cadastrado em banco
     * @return    retorna uma Mono stream com o cliente salvo no formato JSON
     */
    @DeleteMapping(value="/{id}")
    @ResponseStatus(code=HttpStatus.OK)
    @RolesAllowed({"SUPER_ADMIN"})
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

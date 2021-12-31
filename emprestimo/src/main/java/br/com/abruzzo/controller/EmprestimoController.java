package br.com.abruzzo.controller;

import br.com.abruzzo.config.ParametrosConfig;
import br.com.abruzzo.dto.EmprestimoDTO;
import br.com.abruzzo.exceptions.ErroOperacaoTransacionalBancoException;
import br.com.abruzzo.model.Emprestimo;
import br.com.abruzzo.service.EmprestimoService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
/**
 * @author Emmanuel Abruzzo
 * @date 26/12/2021
 */
/**
 * Rest controller que expõe os recursos da API
 * @author Emmanuel Abruzzo
 * @date 25/12/2021
 */
@RestController
@Slf4j
@RequestMapping("/emprestimo")
public class EmprestimoController {


    private static final Logger logger = LoggerFactory.getLogger(EmprestimoController.class);

    private EmprestimoService emprestimoService;

    @Autowired
    private ModelMapper modelMapper;

    public EmprestimoController(EmprestimoService emprestimoService) {
        this.emprestimoService = emprestimoService;
    }

    /**
     *  Método que retorna um emprestimo conforme id especificado,
     *  após um GET request via chamada Rest
     * @param id  Id do emprestimo cadastrado em banco
     * @return    retorna um emprestimo DTO no formato JSON
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public EmprestimoDTO getEmprestimoById(@PathVariable Long id) {

        logger.info("Chegou GET request no Endpoint {}/{}/{}",
                ParametrosConfig.ENDPOINT_BASE.getValue()
                , ParametrosConfig.EMPRESTIMO_ENDPOINT.getValue(),id);

        Optional<Emprestimo> emprestimo = emprestimoService.findById(id);


        if (emprestimo.isPresent()) {
            EmprestimoDTO emprestimoDTO = this.modelMapper.map(emprestimo.get(),EmprestimoDTO.class);
            return emprestimoDTO;
        }
        else return new EmprestimoDTO();
    }


    /**
     *  Método que retorna uma lista de Emprestimo DTO
     *  de todos os emprestimos cadastrados na base
     *  após um GET request via chamada Rest
     *
     * @return    retorna um ResponseEntity de lista de DTOs de emprestimos no formato JSON
     */
    @GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<EmprestimoDTO>> retornaTodosEmprestimos(){

        logger.info("Chegou GET request no Endpoint {}/{}", ParametrosConfig.ENDPOINT_BASE.getValue()
                , ParametrosConfig.EMPRESTIMO_ENDPOINT.getValue());

        ResponseEntity resposta = ResponseEntity.notFound().build();
        List<EmprestimoDTO> listaEmprestimoDTO = new ArrayList<>();
        try {
            List<Emprestimo> listaEmprestimos = emprestimoService.findAll();
            listaEmprestimos.stream().forEach(emprestimo ->{

                EmprestimoDTO emprestimoDTO = this.modelMapper.map(emprestimo,EmprestimoDTO.class);
                listaEmprestimoDTO.add(emprestimoDTO);

            });

            return ResponseEntity.ok().body(listaEmprestimoDTO);

        }catch(Exception exception){

            return resposta;

        }
    }



    /**
     *  Método responsável por cadastrar um emprestimo na base de dados
     *  após uma request via chamada Rest utilizando o método HTTP POST
     *
     * @param emprestimoDTO Objeto DTO JSON emprestimo que chega como payload no request body
     * @return    retorna um DTO com o emprestimo salvo no formato JSON
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EmprestimoDTO> criarEmprestimoConsolidado(@RequestBody EmprestimoDTO emprestimoDTO) throws ErroOperacaoTransacionalBancoException {

        logger.info("Requisição para salvar um emprestimo na base");
        logger.info("POST recebido no seguinte endpoint: {}", ParametrosConfig.ENDPOINT_BASE.getValue());

        Emprestimo emprestimoSalvo = null;
        try{
            Emprestimo emprestimo = this.modelMapper.map(emprestimoDTO, Emprestimo.class);
            emprestimoSalvo = emprestimoService.save(emprestimo);
            emprestimoDTO = this.modelMapper.map(emprestimoSalvo,EmprestimoDTO.class);
            logger.info("Emprestimo %s foi salvo", emprestimo.toString());
            logger.info("%s", emprestimo);
            return ResponseEntity.ok().body(emprestimoDTO);

        }catch(Exception erro){
            throw new ErroOperacaoTransacionalBancoException(erro.getLocalizedMessage(), logger);
        }
    }


    /**
     *  Método responsável por atualizar um emprestimo na base de dados
     *  após uma request via chamada Rest utilizando o método HTTP PUT
     *
     * @param id  Id do emprestimo cadastrado em banco
     * @param emprestimoUpdatedDTO emprestimo DTO JSON emprestimo que chega como payload no request body
     * @return    retorna um DTO com o emprestimo salvo no formato JSON
     */
    @PutMapping(value = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EmprestimoDTO> atualizaEmprestimo(@PathVariable Long id,
                                                   @RequestBody EmprestimoDTO emprestimoUpdatedDTO) throws ErroOperacaoTransacionalBancoException {

        logger.info("Requisição para atualizar um emprestimo na base");
        logger.info("PUT request recebido no seguinte endpoint: {}", ParametrosConfig.ENDPOINT_BASE.getValue());
        emprestimoUpdatedDTO.setId(id);

        try {
            Emprestimo emprestimo = this.modelMapper.map(emprestimoUpdatedDTO,Emprestimo.class);
            Emprestimo emprestimoUpdated = emprestimoService.save(emprestimo);
            emprestimoUpdatedDTO = this.modelMapper.map(emprestimoUpdated,EmprestimoDTO.class);
            logger.info("Empréstimo atualizado %s",emprestimoUpdated);
            return ResponseEntity.ok().body(emprestimoUpdatedDTO);
        }catch(Exception erro){
            throw new ErroOperacaoTransacionalBancoException(erro.getLocalizedMessage(), logger);
        }

    }



    /**
     *  Método responsável por receber o request para deletar um emprestimo na base de dados
     *  após uma request via chamada Rest utilizando o método HTTP DELETE
     *
     * @param id  Id do emprestimo cadastrado em banco
     *
     */
    @DeleteMapping(value="{id}")
    @ResponseStatus(code=HttpStatus.OK)
    public ResponseEntity<String> delete(@PathVariable Long id) throws ErroOperacaoTransacionalBancoException {
        logger.info("DELETE request recebido no endpoint: {}", ParametrosConfig.ENDPOINT_BASE.getValue());
        try{
            emprestimoService.deleteById(id);
            logger.info("Emprestimo com id {} foi deletado", id);
            return ResponseEntity.ok().body(String.format("Emprestimo com id {} foi deletado", id));
        }catch (Exception erro){
            throw new ErroOperacaoTransacionalBancoException(erro.getLocalizedMessage(), logger);
        }
    }
}
package br.com.abruzzo.controller;

import br.com.abruzzo.config.ParametrosConfig;
import br.com.abruzzo.exceptions.ErroOperacaoTransacionalBancoException;
import br.com.abruzzo.model.Emprestimo;
import br.com.abruzzo.service.EmprestimoService;
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

    public EmprestimoController(EmprestimoService emprestimoService) {
        this.emprestimoService = emprestimoService;
    }

    /**
     *  Método que retorna um emprestimo conforme id especificado,
     *  após um GET request via chamada Rest
     * @param id  Id do emprestimo cadastrado em banco
     * @return    retorna um emprestimo no formato JSON
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Emprestimo> getEmprestimoById(@PathVariable Long id) {

        logger.info("Chegou GET request no Endpoint {}/{}/{}",
                ParametrosConfig.ENDPOINT_BASE.getValue()
                , ParametrosConfig.EMPRESTIMO_ENDPOINT.getValue(),id);

        Optional<Emprestimo> emprestimo = emprestimoService.findById(id);

        if (emprestimo.isPresent()) return ResponseEntity.ok().body(emprestimo.get());
        else return (ResponseEntity) ResponseEntity.notFound().build();

    }


    /**
     *  Método que retorna um Flux de todos os emprestimos cadastrados na base
     *  após um GET request via chamada Rest
     *
     * @return    retorna um Flux stream de emprestimos no formato JSON
     */
    @GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<Emprestimo> retornaTodosEmprestimos(){
        logger.info("Chegou GET request no Endpoint {}/{}", ParametrosConfig.ENDPOINT_BASE.getValue()
                , ParametrosConfig.EMPRESTIMO_ENDPOINT.getValue());
        return emprestimoService.findAll();
    }



    /**
     *  Método responsável por cadastrar um emprestimo na base de dados
     *  após uma request via chamada Rest utilizando o método HTTP POST
     *
     * @param emprestimo Objeto JSON emprestimo que chega como payload no request body
     * @return    retorna uma Mono stream com o emprestimo salvo no formato JSON
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Emprestimo createEmprestimo(@RequestBody Emprestimo emprestimo) throws ErroOperacaoTransacionalBancoException {

        logger.info("Requisição para salvar um emprestimo na base");
        logger.info("POST recebido no seguinte endpoint: {}", ParametrosConfig.ENDPOINT_BASE.getValue());

        Emprestimo emprestimoSalvo = null;
        try{
            emprestimoSalvo = emprestimoService.save(emprestimo);
        }catch(Exception erro){

            throw new ErroOperacaoTransacionalBancoException(erro.getLocalizedMessage(), logger);

        }
        logger.info("Emprestimo {} foi salvo", emprestimo);
        logger.info("{}", emprestimo);
        return emprestimoSalvo;
    }


    /**
     *  Método responsável por atualizar um emprestimo na base de dados
     *  após uma request via chamada Rest utilizando o método HTTP PUT
     *
     * @param id  Id do emprestimo cadastrado em banco
     * @param emprestimoUpdated emprestimo Objeto JSON emprestimo que chega como payload no request body
     * @return    retorna uma Mono stream com o emprestimo salvo no formato JSON
     */
    @PutMapping(value = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Emprestimo> atualizaEmprestimo(@PathVariable Long id,
                                                   @RequestBody Emprestimo emprestimoUpdated) throws ErroOperacaoTransacionalBancoException {

        logger.info("Requisição para atualizar um emprestimo na base");
        logger.info("PUT request recebido no seguinte endpoint: {}", ParametrosConfig.ENDPOINT_BASE.getValue());
        emprestimoUpdated.setId(id);
        ResponseEntity<Emprestimo> resposta = null;
        try {
            emprestimoService.save(emprestimoUpdated);
        }catch(Exception erro){
            resposta = ResponseEntity.notFound().build();
            throw new ErroOperacaoTransacionalBancoException(erro.getLocalizedMessage(), logger);
        }
        return resposta;

    }



    /**
     *  Método responsável por receber o request para deletar um emprestimo na base de dados
     *  após uma request via chamada Rest utilizando o método HTTP DELETE
     *
     * @param id  Id do emprestimo cadastrado em banco
     * @return    retorna uma Mono stream com o emprestimo salvo no formato JSON
     */
    @DeleteMapping(value="{id}")
    @ResponseStatus(code=HttpStatus.OK)
    public void delete(@PathVariable Long id) throws ErroOperacaoTransacionalBancoException {
        logger.info("DELETE request recebido no endpoint: {}", ParametrosConfig.ENDPOINT_BASE.getValue());
        try{
            emprestimoService.deleteById(id);
            logger.info("Emprestimo com id {} foi deletado", id);
        }catch (Exception erro){
            throw new ErroOperacaoTransacionalBancoException(erro.getLocalizedMessage(), logger);
        }
    }


}

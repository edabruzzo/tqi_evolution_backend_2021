package br.com.abruzzo.controller;

import br.com.abruzzo.config.ParametrosConfig;
import br.com.abruzzo.exceptions.ErroOperacaoTransacionalBancoException;
import br.com.abruzzo.model.SolicitacaoEmprestimo;
import br.com.abruzzo.service.SolicitacaoEmprestimoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author Emmanuel Abruzzo
 * @date 29/12/2021
 */
@RestController
@RequestMapping("/solicitacao_emprestimo")
public class SolicitacaoEmprestimoController {

    private static final Logger logger = LoggerFactory.getLogger(SolicitacaoEmprestimoController.class);


    SolicitacaoEmprestimoService solicitacaoEmprestimoService;


    public SolicitacaoEmprestimoController(SolicitacaoEmprestimoService solicitacaoEmprestimoService) {
        this.solicitacaoEmprestimoService = solicitacaoEmprestimoService;
    }

    /**
     *  Método responsável por cadastrar uma solicitação de Emprestimo
     *  após uma request via chamada Rest utilizando o método HTTP POST
     *
     * @param solicitacaoEmprestimo Objeto JSON solicitacaoEmprestimo que chega como payload no request body
     * @return    retorna uma entidade SolicitacaoEmprestimo com a solicitação de solicitacaoEmprestimo em processamento no formato JSON
     */
    @PostMapping( consumes = MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<SolicitacaoEmprestimo> criaSolicitacaoEmprestimo(@RequestBody SolicitacaoEmprestimo solicitacaoEmprestimo) throws ErroOperacaoTransacionalBancoException {

        logger.info("Requisição para solicitar um emprestimo");
        logger.info("POST recebido no seguinte endpoint: {}", ParametrosConfig.ENDPOINT_BASE.getValue());
        try {
            SolicitacaoEmprestimo solicitacaoSalva = solicitacaoEmprestimoService.save(solicitacaoEmprestimo);
            return ResponseEntity.ok().body(solicitacaoSalva);
        }catch(Exception erro){
            return ResponseEntity.status(500).build();
        }
    }



    /**
     *
     * Método que retorna uma solicitacao de emprestimo conforme idSolicitacaoEmprestimo,
     * idCliente ou cpfCliente especificado, após um GET request via chamada Rest
     *
     * @param idCliente
     * @param cpfCliente
     * @param idSolicitacaoEmprestimo
     * @return
     */
    @GetMapping(value="/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<SolicitacaoEmprestimo> getSolicitacaoEmprestimoById(
            @PathVariable(name="id",required=true) Long idSolicitacaoEmprestimo,
            @RequestParam(name="idCliente", required=false) Long idCliente,
            @RequestParam(name="cpfCliente",required=false) String cpfCliente){

        logger.info("Chegou GET request no Endpoint %s/%s",ParametrosConfig.ENDPOINT_BASE.getValue(), ParametrosConfig.ENDPOINT_BASE.getValue());

        Optional<SolicitacaoEmprestimo> solicitacaoEmprestimo = solicitacaoEmprestimoService.verificarSolicitacaoEmprestimo(idSolicitacaoEmprestimo, idCliente,cpfCliente);

        if (solicitacaoEmprestimo.isPresent()) return ResponseEntity.ok().body(solicitacaoEmprestimo.get());
        else return (ResponseEntity) ResponseEntity.notFound().build();

    }


    /**
     *  Método que retorna todas as solicitacao de Emprestimos cadastrados na base
     *  após um GET request via chamada Rest
     *
     * @return    retorna uma Lista de solicitacaoEmprestimos no formato JSON
     */
    @GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<SolicitacaoEmprestimo> retornaTodasSolicitacaoEmprestimos(){
        logger.info("Chegou GET request no Endpoint %s", ParametrosConfig.ENDPOINT_BASE.getValue());
        return solicitacaoEmprestimoService.findAll();
    }
    
    


}

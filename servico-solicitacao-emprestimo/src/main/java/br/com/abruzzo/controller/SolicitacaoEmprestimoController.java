package br.com.abruzzo.controller;

import br.com.abruzzo.config.ParametrosConfig;
import br.com.abruzzo.dto.SolicitacaoEmprestimoDTO;
import br.com.abruzzo.exceptions.ErroOperacaoTransacionalBancoException;
import br.com.abruzzo.model.SolicitacaoEmprestimo;
import br.com.abruzzo.model.SolicitacaoEmprestimoStatus;
import br.com.abruzzo.service.SolicitacaoEmprestimoService;
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


/**
 * @author Emmanuel Abruzzo
 * @date 29/12/2021
 */
@RestController
@RequestMapping("/solicitacao_emprestimo")
public class SolicitacaoEmprestimoController {

    private static final Logger logger = LoggerFactory.getLogger(SolicitacaoEmprestimoController.class);


    SolicitacaoEmprestimoService solicitacaoEmprestimoService;

    @Autowired
    ModelMapper modelMapper;

    public SolicitacaoEmprestimoController(SolicitacaoEmprestimoService solicitacaoEmprestimoService) {
        this.solicitacaoEmprestimoService = solicitacaoEmprestimoService;
    }

    /**
     *  Método responsável por cadastrar uma solicitação de Emprestimo
     *  após uma request via chamada Rest utilizando o método HTTP POST
     *
     * @param solicitacaoEmprestimoDTO Objeto DTO JSON solicitacaoEmprestimo que chega como payload no request body
     * @return    retorna uma entidade SolicitacaoEmprestimoDTO com a solicitação de Emprestimo em processamento no formato JSON
     */
    @PostMapping( consumes = MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public SolicitacaoEmprestimoDTO criaSolicitacaoEmprestimo(@RequestBody SolicitacaoEmprestimoDTO solicitacaoEmprestimoDTO) throws ErroOperacaoTransacionalBancoException {

        logger.info("Requisição para solicitar um emprestimo %s",solicitacaoEmprestimoDTO);

        logger.info("POST recebido no seguinte endpoint: {}", ParametrosConfig.ENDPOINT_BASE.getValue());

        try {
            SolicitacaoEmprestimo solicitacaoEmprestimo = this.modelMapper.map(solicitacaoEmprestimoDTO,SolicitacaoEmprestimo.class);

            solicitacaoEmprestimo = solicitacaoEmprestimoService.save(solicitacaoEmprestimo);

            solicitacaoEmprestimoDTO = this.modelMapper.map(solicitacaoEmprestimo,SolicitacaoEmprestimoDTO.class);
            logger.info("Solicitação de empréstimo salva %s",solicitacaoEmprestimo);


        }catch(Exception erro){

            solicitacaoEmprestimoDTO.setStatus(String.valueOf(SolicitacaoEmprestimoStatus.PROBLEMA_AO_SALVAR));

            String mensagemErro = String.format("Erro ao salvar solicitação de empréstimo %s",solicitacaoEmprestimoDTO);

            throw new ErroOperacaoTransacionalBancoException(mensagemErro,logger);

        }finally{
            return solicitacaoEmprestimoDTO;
        }

    }



    /**
     *
     * Método que retorna uma lista com os DTOs de solicitacao de emprestimo conforme,
     * idCliente ou cpfCliente especificado, após um GET request via chamada Rest
     *
     * @param idCliente
     * @param cpfCliente
     * @return Lista de Solicitações de Emprestimo DTO
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<SolicitacaoEmprestimoDTO> getSolicitacoesEmprestimoCliente(
            @RequestParam(name="idCliente", required=false) Long idCliente,
            @RequestParam(name="cpfCliente",required=false) String cpfCliente){

        String mensagemLog = String.format("Requisição para listar solicitações de empréstimo do Cliente -> idCliente %s, cpfCliente %s",idCliente, cpfCliente);
        logger.info("Chegou GET request no Endpoint %s:%s",ParametrosConfig.ENDPOINT_BASE.getValue(), ParametrosConfig.ENDPOINT_BASE.getValue());
        logger.info(mensagemLog);

        List<SolicitacaoEmprestimo> listaSolicitacoesEmprestimoCliente = solicitacaoEmprestimoService.verificarSolicitacoesEmprestimoCliente(idCliente,cpfCliente);
        List<SolicitacaoEmprestimoDTO> listaSolicitacoesEmprestimoClienteDTO = new ArrayList<>();

        listaSolicitacoesEmprestimoCliente.stream().forEach(solicitacaoEmprestimo -> {

            SolicitacaoEmprestimoDTO solicitacaoEmprestimoDTO = this.modelMapper.map(solicitacaoEmprestimo,SolicitacaoEmprestimoDTO.class);
            listaSolicitacoesEmprestimoClienteDTO.add(solicitacaoEmprestimoDTO);
        });

        return listaSolicitacoesEmprestimoClienteDTO;

    }


    /**
     *  Método que retorna todas as solicitações de Emprestimos DTO cadastrados na base
     *  após um GET request via chamada Rest
     *
     * @return    retorna uma Lista de solicitacaoEmprestimos DTO no formato JSON
     */
    @GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<SolicitacaoEmprestimoDTO> retornaTodasSolicitacaoEmprestimos(){

        logger.info("Chegou GET request no Endpoint %s para listar todas as solicitações de empréstimos", ParametrosConfig.ENDPOINT_BASE.getValue());
        List<SolicitacaoEmprestimoDTO> listaSolicitacaoEmprestimoDTO = new ArrayList<>();

        try {
            List<SolicitacaoEmprestimo> listaSolicitacaoEmprestimo = solicitacaoEmprestimoService.findAll();
            listaSolicitacaoEmprestimo.stream().forEach(solicitacaoEmprestimo ->{

                SolicitacaoEmprestimoDTO solicitacaoEmprestimoDTO = this.modelMapper.map(solicitacaoEmprestimo, SolicitacaoEmprestimoDTO.class);

                listaSolicitacaoEmprestimoDTO.add(solicitacaoEmprestimoDTO);


            });

        }catch(Exception erro){
            logger.error("Erro ao listar todas as Solicitações de empréstimo");
        }

        return listaSolicitacaoEmprestimoDTO;
    }
    
    


}

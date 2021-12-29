package br.com.abruzzo.controller;

import br.com.abruzzo.avaliacao_emprestimos.Avaliacao;
import br.com.abruzzo.config.ParametrosConfig;
import br.com.abruzzo.dto.EmprestimoDTO;
import br.com.abruzzo.exceptions.ErroOperacaoTransacionalBancoException;
import br.com.abruzzo.model.SolicitacaoEmprestimo;
import br.com.abruzzo.service.SolicitacaoEmprestimoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @author Emmanuel Abruzzo
 * @date 29/12/2021
 */
@RestController
@RequestMapping("/solicitar_emprestimo")
public class SolicitacaoEmprestimoController {

    private static final Logger logger = LoggerFactory.getLogger(SolicitacaoEmprestimoController.class);


    SolicitacaoEmprestimoService solicitacaoEmprestimoService;


    public SolicitacaoEmprestimoController(SolicitacaoEmprestimoService solicitacaoEmprestimoService) {
        this.solicitacaoEmprestimoService = solicitacaoEmprestimoService;
    }

    /**
     *  Método responsável por cadastrar uma solicitação de emprestimo
     *  após uma request via chamada Rest utilizando o método HTTP POST
     *
     * @param solicitacaoEmprestimo Objeto JSON emprestimo que chega como payload no request body
     * @return    retorna uma entidade SolicitacaoEmprestimo com a solicitação de emprestimo em processamento no formato JSON
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public SolicitacaoEmprestimo criaSolicitacaoEmprestimo(@RequestBody SolicitacaoEmprestimo solicitacaoEmprestimo) throws ErroOperacaoTransacionalBancoException {

        logger.info("Requisição para solicitar um emprestimo");
        logger.info("POST recebido no seguinte endpoint: {}", ParametrosConfig.ENDPOINT_BASE.getValue());

        SolicitacaoEmprestimo solicitacaoEmprestimoSalva = null;
        try{
            solicitacaoEmprestimoSalva.setStatus("Em processamento");
            solicitacaoEmprestimoSalva = solicitacaoEmprestimoService.save(solicitacaoEmprestimo);
            boolean emprestimoAprovado = Avaliacao.enviarParaProcessamento(solicitacaoEmprestimoSalva);
        }catch(Exception erro){
            String mensagem = "Erro ao salvar a solicitação de empréstimno em banco";
            throw new ErroOperacaoTransacionalBancoException(mensagem, logger);

        }
        logger.info("Solicitação de empréstimo salva");
        logger.info("{}", solicitacaoEmprestimoSalva);
        return solicitacaoEmprestimoSalva;
    }




}

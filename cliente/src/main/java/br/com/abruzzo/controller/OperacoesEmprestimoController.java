package br.com.abruzzo.controller;

import br.com.abruzzo.config.ParametrosConfig;
import br.com.abruzzo.exceptions.ErroNegocioAutorizacaoEmprestimoException;
import br.com.abruzzo.service.OperacoesEmprestimoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

/**
 * @link https://howtodoinjava.com/spring-boot2/resttemplate/resttemplate-post-json-example/
 * @link https://www.baeldung.com/spring-resttemplate-post-json
 * @author Emmanuel Abruzzo
 * @date 26/12/2021
 */

@RestController
@RequestMapping("/emprestimo")
public class OperacoesEmprestimoController {

    private static final Logger logger = LoggerFactory.getLogger(ClienteController.class);

    OperacoesEmprestimoService operacoesEmprestimoService;

    public OperacoesEmprestimoController(OperacoesEmprestimoService operacoesEmprestimoService) {
        this.operacoesEmprestimoService = operacoesEmprestimoService;
    }

    @GetMapping(value="/solicitar",
            consumes = MediaType.ALL_VALUE,
            produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> solicitacaoEmprestimo(@RequestParam(name = "idCliente") Long idCliente,
                                      @RequestParam(name = "valor") double valor,
                                      @RequestParam(name = "parcelas") int parcelas,
                                      @RequestParam(name = "dataPrimeiraParcela") Date dataPrimeiraParcela)  {

        logger.info("Requisição -> Solicitação de empréstimo -> ");
        logger.info("IdCliente: {} | Valor do empréstimo: R$ {} | Parcelas: {} | Data primeira parcela: {}",
                    idCliente, valor, parcelas, dataPrimeiraParcela);
        logger.info("GET recebido no seguinte endpoint: {}", ParametrosConfig.ENDPOINT_BASE.getValue());

        ResponseEntity resposta =  ResponseEntity.unprocessableEntity().build();

        try{
            ResponseEntity<String> resultado = operacoesEmprestimoService.solicitarEmprestimo(idCliente,valor,parcelas,dataPrimeiraParcela);
            logger.info("Solicitatão de empréstimo realizada com sucesso para o cliente com id: {}", idCliente);
            return resultado;
        }catch(Exception erro){

            try {
                throw new ErroNegocioAutorizacaoEmprestimoException(erro.getLocalizedMessage(), logger);
            } catch (ErroNegocioAutorizacaoEmprestimoException e) {
                e.printStackTrace();
                return resposta;
            }

        }

    }

}
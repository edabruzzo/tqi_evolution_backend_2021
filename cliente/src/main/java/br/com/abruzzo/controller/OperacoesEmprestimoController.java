package br.com.abruzzo.controller;

import br.com.abruzzo.config.ParametrosConfig;
import br.com.abruzzo.dto.SolicitacaoEmprestimoDTO;
import br.com.abruzzo.dto.SolicitacaoEmprestimoStatusDTO;
import br.com.abruzzo.service.OperacoesEmprestimoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.sql.Date;

/**
 *
 * Classe responsável por invocar o Service para realizar uma solicitação de empréstimo
 * Não permitimos que o cliente faça a solicitação.
 *
 * Estamos supondo que exista uma agência física e, por isso, apenas funcionários e admins podem
 * realizar solicitações de empréstimo no atendimento ao cliente, que estará em posse de seus
 * documentos e comprovantes de renda.
 *
 * Estamos anotando o layer controller como seguro e apenas aberto a roles específicas
 *
 * usando a anotaçaõ do JSR-250
 *
 * Para mais informações, vide: https://www.baeldung.com/spring-security-method-security
 *
 *
 * @link https://howtodoinjava.com/spring-boot2/resttemplate/resttemplate-post-json-example/
 * @link https://www.baeldung.com/spring-resttemplate-post-json
 *
 * @author Emmanuel Abruzzo
 * @date 26/12/2021
 */

@RestController
@RequestMapping("/emprestimo")
@RolesAllowed({"FUNCIONARIO","SUPER_ADMIN"})
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
    public SolicitacaoEmprestimoDTO solicitacaoEmprestimo(@RequestParam(name = "idCliente") Long idCliente,
                                                                          @RequestParam(name = "valor") double valor,
                                                                          @RequestParam(name = "parcelas") int parcelas,
                                                                          @RequestParam(name = "dataPrimeiraParcela") Date dataPrimeiraParcela)  {

        logger.info("Requisição -> Solicitação de empréstimo -> ");
        logger.info("IdCliente: {} | Valor do empréstimo: R$ {} | Parcelas: {} | Data primeira parcela: {}",
                    idCliente, valor, parcelas, dataPrimeiraParcela);
        logger.info("GET recebido no seguinte endpoint: {}", ParametrosConfig.ENDPOINT_BASE.getValue());

        SolicitacaoEmprestimoDTO solicitacaoEmprestimoDTO = new SolicitacaoEmprestimoDTO();
        solicitacaoEmprestimoDTO.setIdCliente(idCliente);
        solicitacaoEmprestimoDTO.setValor(valor);
        solicitacaoEmprestimoDTO.setNumeroMaximoParcelas(parcelas);
        solicitacaoEmprestimoDTO.setData_primeira_parcela(dataPrimeiraParcela);

        solicitacaoEmprestimoDTO.setStatus(String.valueOf(SolicitacaoEmprestimoStatusDTO.ABERTA));

        try{
            solicitacaoEmprestimoDTO = operacoesEmprestimoService.solicitarEmprestimo(solicitacaoEmprestimoDTO);

        }catch(Exception erro){
            erro.printStackTrace();
            logger.error(erro.getMessage());

        }

        return solicitacaoEmprestimoDTO;

    }

}

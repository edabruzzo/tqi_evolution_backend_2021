package br.com.abruzzo.frontend_cliente_emprestimo.feign_clients;

import br.com.abruzzo.frontend_cliente_emprestimo.dto.SolicitacaoEmprestimoDTO;
import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 *
 * Feign client para requests para o microservice de solicitação de empréstimos
 * que utiliza o Redis para guardar solicitações em processamento em memória
 *
 * No momento de listar as solicitações de empréstimo de um cliente específico
 * podemos fazer sobrecarga de métodos feign requests, pois deixamos os parâmetros
 * idCliente e cpfCliente como opcionais lá na API Rest do outro microsserviço
 * que expõe o método para listar solicitações de empréstimo
 *
 *
 *
 * @author Emmanuel Abruzzo
 * @date 02/01/2022
 */
@FeignClient("servico-solicitacao-emprestimo")
public interface ISolicitacaoEmprestimoFeignClient {

    @RequestMapping(path= "/solicitacao_emprestimo?idCliente={idCliente}&cpfCliente={cpfCliente}",method= RequestMethod.GET)
    public List<SolicitacaoEmprestimoDTO> getSolicitacoesEmprestimoCliente(@Param("idCliente") Long idCliente,
                                                                           @Param("cpfCliente") String cpfCliente);


    @RequestMapping(path= "/solicitacao_emprestimo?cpfCliente={cpfCliente}",method= RequestMethod.GET)
    public List<SolicitacaoEmprestimoDTO> getSolicitacoesEmprestimoCliente(@Param("cpfCliente") String cpfCliente);


    @RequestMapping(path= "/solicitacao_emprestimo?idCliente={idCliente}",method= RequestMethod.GET)
    public List<SolicitacaoEmprestimoDTO> getSolicitacoesEmprestimoCliente(@Param("idCliente") Long idCliente);


    @RequestMapping(path= "solicitacao-emprestimo",method= RequestMethod.GET)
    public List<SolicitacaoEmprestimoDTO> retornaTodasSolicitacaoEmprestimos();

    @RequestMapping(path= "solicitacao-emprestimo",method= RequestMethod.POST)
    public SolicitacaoEmprestimoDTO criaSolicitacaoEmprestimo(SolicitacaoEmprestimoDTO solicitacaoEmprestimoDTO);

   }
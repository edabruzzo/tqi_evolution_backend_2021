package br.com.abruzzo.frontend_cliente_emprestimo.feign_clients;

import br.com.abruzzo.frontend_cliente_emprestimo.dto.SolicitacaoEmprestimoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author Emmanuel Abruzzo
 * @date 02/01/2022
 */
@FeignClient("servico-solicitacao-emprestimo")
public interface ISolicitacaoEmprestimoFeignClient {

    @RequestMapping(path= "solicitacao_emprestimo",method= RequestMethod.GET)
    public List<SolicitacaoEmprestimoDTO> getSolicitacoesEmprestimoCliente(Long idCliente, String cpfCliente);

    @RequestMapping(path= "solicitacao-emprestimo",method= RequestMethod.GET)
    public List<SolicitacaoEmprestimoDTO> retornaTodasSolicitacaoEmprestimos();

    @RequestMapping(path= "solicitacao-emprestimo",method= RequestMethod.POST)
    public SolicitacaoEmprestimoDTO criaSolicitacaoEmprestimo(SolicitacaoEmprestimoDTO solicitacaoEmprestimoDTO);

   }
package br.com.abruzzo.feign_clients;

import br.com.abruzzo.dto.SolicitacaoEmprestimoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Emmanuel Abruzzo
 * @date 31/12/2021
 */

@FeignClient("servico-solicitacao-emprestimo")
public interface SolicitacaoEmprestimoFeignClient {


    @RequestMapping(path= "solicitacao-emprestimo",method= RequestMethod.POST)
    public SolicitacaoEmprestimoDTO criaSolicitacaoEmprestimo(SolicitacaoEmprestimoDTO solicitacaoEmprestimoDTO);


}

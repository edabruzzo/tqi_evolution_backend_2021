package br.com.abruzzo.client;

import br.com.abruzzo.dto.SolicitacaoEmprestimoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Emmanuel Abruzzo
 * @date 31/12/2021
 */

@FeignClient("servico_solicitacao_emprestimo")
public interface SolicitacaoEmprestimoFeignClient {


    @RequestMapping(path= "solicitacao_emprestimo",method= RequestMethod.POST)
    public ResponseEntity<SolicitacaoEmprestimoDTO> criaSolicitacaoEmprestimo(SolicitacaoEmprestimoDTO solicitacaoEmprestimoDTO);


}

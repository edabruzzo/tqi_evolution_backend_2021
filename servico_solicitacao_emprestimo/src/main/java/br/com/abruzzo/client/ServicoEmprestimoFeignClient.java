package br.com.abruzzo.client;

import br.com.abruzzo.dto.EmprestimoDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Emmanuel Abruzzo
 * @date 31/12/2021
 */

@FeignClient("servico_emprestimo")
public interface ServicoEmprestimoFeignClient {


    @RequestMapping(path="/emprestimo", method= RequestMethod.POST)
    EmprestimoDTO criarEmprestimoConsolidado(EmprestimoDTO emprestimoDTO);
}

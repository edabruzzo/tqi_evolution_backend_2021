package br.com.abruzzo.frontend_cliente_emprestimo.feign_clients;

import br.com.abruzzo.frontend_cliente_emprestimo.dto.EmprestimoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author Emmanuel Abruzzo
 * @date 03/01/2022
 */
@FeignClient("servico-emprestimo")
public interface IEmprestimoFeignClient {

    @RequestMapping(path= "emprestimos/{cpfCliente}",method= RequestMethod.GET)
    List<EmprestimoDTO> retornaTodosEmprestimosByCliente(@PathVariable(name="cpfCliente") String cpfClienteConsultado);

    @RequestMapping(path= "emprestimos",method= RequestMethod.GET)
    List<EmprestimoDTO> retornaTodosEmprestimos();
}

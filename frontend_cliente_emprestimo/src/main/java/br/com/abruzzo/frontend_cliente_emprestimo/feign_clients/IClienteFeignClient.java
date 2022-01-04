package br.com.abruzzo.frontend_cliente_emprestimo.feign_clients;

import br.com.abruzzo.frontend_cliente_emprestimo.dto.ClienteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author Emmanuel Abruzzo
 * @date 01/01/2022
 */
@FeignClient("servico-cliente")
public interface IClienteFeignClient {

    @RequestMapping(path= "cliente",method= RequestMethod.GET)
    public List<ClienteDTO> retornaTodosClientes();


    @RequestMapping(path= "cliente",method= RequestMethod.POST)
    public ClienteDTO criaNovoCliente(ClienteDTO clienteDTO);


}


package br.com.abruzzo.frontend_cliente_emprestimo.service;

import br.com.abruzzo.dto.SolicitacaoClienteEmprestimoDTO;
import br.com.abruzzo.frontend_cliente_emprestimo.client.IClienteFeignClient;
import br.com.abruzzo.frontend_cliente_emprestimo.dto.ClienteDTO;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Emmanuel Abruzzo
 * @date 04/01/2022
 */
public class ClienteService {


    @Autowired
    IClienteFeignClient clienteFeignClient;


    public ClienteDTO criaNovoCliente(SolicitacaoClienteEmprestimoDTO solicitacaoClienteEmprestimoDTO){

        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setCpf(solicitacaoClienteEmprestimoDTO.getCpf());
        clienteDTO.setEmail(solicitacaoClienteEmprestimoDTO.getEmail());
        clienteDTO.setEnderecoCompleto(solicitacaoClienteEmprestimoDTO.getEnderecoCompleto());
        clienteDTO.setNome(solicitacaoClienteEmprestimoDTO.getNome());
        clienteDTO.setRenda(solicitacaoClienteEmprestimoDTO.getRenda());
        clienteDTO.setRg(solicitacaoClienteEmprestimoDTO.getRg());




        /**
         * Após a chamada para @link IClienteFeignClient se tudo correr bem já teremos
         * salvo o cliente no microsserviço responsável pelo gerenciamento de clientes
         * que nos retornará o clienteSalvoDTO já com um idCliente preenchido
         */
        ClienteDTO clienteSalvoDTO = this.clienteFeignClient.criaNovoCliente(clienteDTO);

        return clienteSalvoDTO;
    }




}

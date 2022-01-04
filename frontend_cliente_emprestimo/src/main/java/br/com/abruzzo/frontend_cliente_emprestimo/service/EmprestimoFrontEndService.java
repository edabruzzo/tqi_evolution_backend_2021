package br.com.abruzzo.frontend_cliente_emprestimo.service;

import br.com.abruzzo.frontend_cliente_emprestimo.controller.EmprestimoController;
import br.com.abruzzo.frontend_cliente_emprestimo.dto.EmprestimoDTO;
import br.com.abruzzo.frontend_cliente_emprestimo.exceptions.ClienteTentandoListarCPFOutroClienteException;
import br.com.abruzzo.frontend_cliente_emprestimo.feign_clients.IEmprestimoFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Emmanuel Abruzzo
 * @date 04/01/2022
 */

public class EmprestimoFrontEndService {


    private static final Logger logger = LoggerFactory.getLogger(EmprestimoFrontEndService.class);

    @Autowired
    IEmprestimoFeignClient emprestimoFeignClient;



    @RolesAllowed({"CLIENTE","FUNCIONARIO","ADMIN"})
    public List<EmprestimoDTO> retornaTodosEmprestimosByCliente(String cpfClienteConsultado) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean usuarioLogadoCliente = authentication.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("CLIENTE"));

        /**
         * Isso só é possível pois estou usando o cpfCliente como username no servidor de aplicação.
         * Do contrário, teria que bater lá no serviço de cliente com Feign
         */
        String cpfClienteLogado = authentication.getName();

        boolean estaTentandoListarEmprestimosOutroCliente = cpfClienteLogado != cpfClienteConsultado;

        if(usuarioLogadoCliente && estaTentandoListarEmprestimosOutroCliente){

            String credenciaisUsuarioLogado = authentication.getCredentials().toString();

            String mensagemErro = "Tentativa de um Cliente listar os empréstimos de outra pessoa\n";
            mensagemErro += String.format("Usuário que fez a tentativa %s\n",credenciaisUsuarioLogado);
            mensagemErro += String.format("Cliente com CPF %s está tendando  listar os empréstimos do cliente de CPF %s"
                                          ,cpfClienteLogado,cpfClienteConsultado);

            throw new ClienteTentandoListarCPFOutroClienteException(mensagemErro, this.logger);
        }

        try{
            List<EmprestimoDTO> listaEmprestimoDTO = emprestimoFeignClient.retornaTodosEmprestimosByCliente(cpfClienteLogado);

            return listaEmprestimoDTO;

        }catch(Exception exception){
            logger.error(exception.getLocalizedMessage());
            return new ArrayList<EmprestimoDTO>();

        }


    }


    @RolesAllowed({"FUNCIONARIO","SUPER_ADMIN"})
    public List<EmprestimoDTO> retornaTodosEmprestimos() {
       return emprestimoFeignClient.retornaTodosEmprestimos();
    }
}

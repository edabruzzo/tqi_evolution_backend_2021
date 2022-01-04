package br.com.abruzzo.frontend_cliente_emprestimo.controller;

import br.com.abruzzo.frontend_cliente_emprestimo.feign_clients.IEmprestimoFeignClient;
import br.com.abruzzo.frontend_cliente_emprestimo.dto.EmprestimoDTO;
import br.com.abruzzo.frontend_cliente_emprestimo.exceptions.ClienteTentandoListarCPFOutroClienteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Emmanuel Abruzzo
 * @date 01/01/2022
 */
@RequestMapping("/emprestimos")
public class EmprestimoController {


    private static final Logger logger = LoggerFactory.getLogger(EmprestimoController.class);

    @Autowired
    IEmprestimoFeignClient emprestimoFeignClient;



    /**
     *  Método que retorna uma lista de Emprestimo DTO
     *  de todos os emprestimos de um cliente específico cadastrados na base
     *  após um GET request via chamada Rest
     *
     *  O cliente não pode consultar o CPF de outro cliente.... se o fizer, será lançada uma exceção
     *  e a tentativa será logada
     *
     * @return    retorna um ResponseEntity de lista de DTOs de emprestimos no formato JSON
     */
    @GetMapping(value="/{cpfCliente}",produces= MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed({"CLIENTE","FUNCIONARIO","SUPER_ADMIN"})
    public List<EmprestimoDTO> retornaTodosEmprestimosByCliente(@PathVariable String cpfClienteConsultado){


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
            mensagemErro += String.format("Usuário que fez a tentativa %s",credenciaisUsuarioLogado);

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





    /**
     *  Método que retorna uma lista de Emprestimo DTO
     *  de todos os emprestimos cadastrados na base
     *  após um GET request via chamada Rest
     *
     * @return    retorna um ResponseEntity de lista de DTOs de emprestimos no formato JSON
     */
    @GetMapping(produces= MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed({"FUNCIONARIO","SUPER_ADMIN"})
    public List<EmprestimoDTO> retornaTodosEmprestimos(){


        try{
            return emprestimoFeignClient.retornaTodosEmprestimos();

        }catch(Exception exception){
            logger.error(exception.getLocalizedMessage());
            return new ArrayList<EmprestimoDTO>();

        }
    }





}

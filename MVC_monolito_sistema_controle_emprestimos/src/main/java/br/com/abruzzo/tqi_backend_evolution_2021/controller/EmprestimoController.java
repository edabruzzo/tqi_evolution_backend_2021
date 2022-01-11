package br.com.abruzzo.tqi_backend_evolution_2021.controller;

import br.com.abruzzo.tqi_backend_evolution_2021.dto.EmprestimoDTO;
import br.com.abruzzo.tqi_backend_evolution_2021.service.EmprestimoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.annotation.security.RolesAllowed;
import java.util.List;

/**
 * @author Emmanuel Abruzzo
 * @date 06/01/2022
 */
@Controller
@RequestMapping("/emprestimo")
public class EmprestimoController {

    private static final Logger logger = LoggerFactory.getLogger(EmprestimoController.class);

    @Autowired
    EmprestimoService emprestimoService;


    /**
     *  Método que retorna uma lista de Emprestimo DTO
     *  de todos os emprestimos de um cliente específico cadastrados na base
     *  após um GET request via chamada Rest
     *
     *  Restrição:
     *  O cliente não pode consultar os empréstimos de outro cliente.... se o fizer, será lançada uma exceção
     *  e a tentativa será logada
     *
     * @return    retorna um ResponseEntity de lista de DTOs de emprestimos no formato JSON
     */
    @GetMapping(value="/{emailCliente}")
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed({"ROLE_CLIENTE","ROLE_FUNCIONARIO","ROLE_SUPER_ADMIN"})
    public String retornaTodosEmprestimosByCliente(@PathVariable String emailCliente, Model model){

        List<EmprestimoDTO> listaEmprestimoDTO = emprestimoService.retornaTodosEmprestimosByCliente(emailCliente);
        model.addAttribute("listaEmprestimos",listaEmprestimoDTO);

        return "emprestimo/listagemEmprestimos";

    }




    /**
     *  Método que retorna uma lista de Emprestimo DTO
     *  de todos os emprestimos cadastrados na base
     *  após um GET request via chamada Rest
     *
     * @return    retorna um ResponseEntity de lista de DTOs de emprestimos no formato JSON
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @RolesAllowed({"ROLE_CLIENTE","ROLE_FUNCIONARIO","ROLE_SUPER_ADMIN"})
    public String retornaTodosEmprestimos(Model model){

        List<EmprestimoDTO> listaEmprestimoDTO = this.emprestimoService.retornaTodosEmprestimos();
        model.addAttribute("listaEmprestimos",listaEmprestimoDTO);
        return "emprestimo/listagemEmprestimos";

    }


}



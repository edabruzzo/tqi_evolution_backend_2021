package br.com.abruzzo.frontend_cliente_emprestimo.controller;

import br.com.abruzzo.dto.SolicitacaoClienteEmprestimoDTO;
import br.com.abruzzo.frontend_cliente_emprestimo.dto.ClienteDTO;
import br.com.abruzzo.frontend_cliente_emprestimo.dto.SolicitacaoEmprestimoDTO;
import br.com.abruzzo.frontend_cliente_emprestimo.dto.UsuarioDTO;
import br.com.abruzzo.frontend_cliente_emprestimo.service.AutenticacaoUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

/**
 * @author Emmanuel Abruzzo
 * @date 04/01/2022
 */

@RequestMapping("/usuarios")
public class UsuarioController {


    @Autowired
    AutenticacaoUsuarioService autenticacaoUsuarioService;

    @GetMapping
    public String usuarioView(Model model){
        return "usuario";
    }


    @RolesAllowed({"FUNCIONARIO", "SUPER_ADMIN"})
    @PostMapping("criar")
    public String criarNovousuario(@ModelAttribute UsuarioDTO usuarioDTO){
        UsuarioDTO usuarioDTOSalvo = this.autenticacaoUsuarioService.criarUsuario(usuarioDTO);
        return "usuario";
    }







}

package br.com.abruzzo.tqi_backend_evolution_2021.controller;

import br.com.abruzzo.tqi_backend_evolution_2021.dto.ClienteDTO;
import br.com.abruzzo.tqi_backend_evolution_2021.dto.UsuarioDTO;
import br.com.abruzzo.tqi_backend_evolution_2021.model.Usuario;
import br.com.abruzzo.tqi_backend_evolution_2021.service.AutenticacaoUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

/**
 * @author Emmanuel Abruzzo
 * @date 06/01/2022
 */
@RequestMapping("/usuarios")
public class UsuarioController {


    @Autowired
    AutenticacaoUsuarioService autenticacaoUsuarioService;


    @GetMapping
    public String usuarioView(Model model){
        return "usuario";
    }



    @PostMapping("criar")
    public String criarNovousuario(@ModelAttribute UsuarioDTO usuarioDTO, Model model){

        UsuarioDTO usuarioDTOSalvo = this.autenticacaoUsuarioService.criarUsuario(usuarioDTO);
        model.addAttribute("usuarioSalvo",usuarioDTOSalvo);
        return "usuario";
    }


    @RolesAllowed({"ROLE_SUPER_ADMIN", "ROLE_FUNCIONARIO"})
    @GetMapping
    public String listarUsuarios(Model model){
        this.atualizarListaClientesDTOTela(model);
        return "usuario";
    }




    private void atualizarListaClientesDTOTela(Model model) {
        List<UsuarioDTO> listaUsuariosDTO = this.autenticacaoUsuarioService.listarUsuarios();
        model.addAttribute("listaUsuarios",listaUsuariosDTO);
    }


    @GetMapping("/edit/{email}")
    @RolesAllowed("ROLE_SUPER_ADMIN")
    public String editarCliente(@PathVariable(name="email") String email, Model model){
        UsuarioDTO usuarioDTO = this.autenticacaoUsuarioService.findByUsername(email);
        model.addAttribute("usuarioDTO",usuarioDTO);
        return "usuario";

    }


    @GetMapping("/delete/{email}")
    @RolesAllowed("ROLE_SUPER_ADMIN")
    public String deletarCliente(@PathVariable("email") String email, Model model){
        this.autenticacaoUsuarioService.delete(email);
        this.atualizarListaClientesDTOTela(model);
        return "cliente";

    }





}

package br.com.abruzzo.tqi_backend_evolution_2021.controller;

import br.com.abruzzo.tqi_backend_evolution_2021.dto.ClienteDTO;
import br.com.abruzzo.tqi_backend_evolution_2021.model.Cliente;
import br.com.abruzzo.tqi_backend_evolution_2021.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;


/**
 * @author Emmanuel Abruzzo
 * @date 06/01/2022
 */
@Controller
@RequestMapping("/cliente")
@RolesAllowed({"ROLE_FUNCIONARIO", "ROLE_SUPER_ADMIN"})
public class ClienteController {


    @Autowired
    ClienteService clienteService;


    @GetMapping()
    public String clientes(Model model){

        model.addAttribute("clienteDTO",new ClienteDTO());
        this.atualizarListaClientesDTOTela(model);
        return "cliente";

    }

    private void atualizarListaClientesDTOTela(Model model) {
        List<ClienteDTO> listaClientesDTO = this.clienteService.listarClientes();
        model.addAttribute("listaClientes",listaClientesDTO);
    }


    @GetMapping("/edit/{id}")
    @RolesAllowed("ROLE_SUPER_ADMIN")
    public String editarCliente(@PathVariable(name="id") Long id, Model model){

        ClienteDTO clienteDTO = this.clienteService.findById(id);
        model.addAttribute("clienteDTO",clienteDTO);
        return "cliente";

    }


    @GetMapping("/delete/{id}")
    @RolesAllowed("ROLE_SUPER_ADMIN")
    public String deletarCliente(@PathVariable("id") Long id, Model model){

        this.clienteService.delete(id);
        this.atualizarListaClientesDTOTela(model);
        return "cliente";

    }



    @PostMapping("cadastrar")
    public String cadastrarCliente(@Valid @ModelAttribute ClienteDTO clienteDTO, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("errorMessage",bindingResult.getFieldErrors().toString());
            return "error";
        }

        this.clienteService.criaNovoCliente(clienteDTO);
        this.clientes(model);

        return "cliente";
    }

}


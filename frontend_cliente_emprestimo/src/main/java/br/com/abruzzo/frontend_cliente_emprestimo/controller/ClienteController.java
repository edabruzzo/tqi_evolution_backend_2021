package br.com.abruzzo.frontend_cliente_emprestimo.controller;

import br.com.abruzzo.frontend_cliente_emprestimo.dto.ClienteDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Emmanuel Abruzzo
 * @date 01/01/2022
 */

@Controller
public class ClienteController {


    @GetMapping("/cliente")
    public String home(Model model){


        ClienteDTO cliente1  = new ClienteDTO(1L,"Andrea","andrea@gmail.com","11111111111","11111111-1","Rua 1",10000d,"123");
        ClienteDTO cliente2 = new ClienteDTO(2L,"José","jose@gmail.com","22222222222","22222222-2","Rua 2",5000d,"123456");
        ClienteDTO cliente3 = new ClienteDTO(3L,"Alberto","alberto@gmail.com","33333333333","33333333-3","Rua 3",15000d,"333333");
        ClienteDTO cliente4 = new ClienteDTO(4L,"Maria","maria@gmail.com","44444444444","44444444-4","Rua 2",8500d,"321654987");

        List<ClienteDTO> listaClientes = new ArrayList<>();
        listaClientes.add(cliente1);
        listaClientes.add(cliente2);
        listaClientes.add(cliente3);
        listaClientes.add(cliente4);

        model.addAttribute(listaClientes);

        return "cliente";



    }


}

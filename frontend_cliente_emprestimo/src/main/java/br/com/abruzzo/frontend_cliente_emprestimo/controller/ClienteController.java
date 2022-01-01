package br.com.abruzzo.frontend_cliente_emprestimo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Emmanuel Abruzzo
 * @date 01/01/2022
 */

@Controller
public class ClienteController {


    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }


}

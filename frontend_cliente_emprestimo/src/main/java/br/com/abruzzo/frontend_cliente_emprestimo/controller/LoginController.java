package br.com.abruzzo.frontend_cliente_emprestimo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Emmanuel Abruzzo
 * @date 04/01/2022
 */
@Controller
public class LoginController {


    @GetMapping
    @RequestMapping("/login")
    public String login() {
        return "login";
    }


}
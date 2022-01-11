package br.com.abruzzo.tqi_backend_evolution_2021.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Emmanuel Abruzzo
 * @date 06/01/2022
 */
@Controller
public class LoginController {

    @GetMapping
    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping
    @RequestMapping("/")
    public String home() {
        return "redirect:/login";
    }
}

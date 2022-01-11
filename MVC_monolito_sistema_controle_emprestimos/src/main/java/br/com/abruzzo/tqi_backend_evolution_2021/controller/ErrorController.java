package br.com.abruzzo.tqi_backend_evolution_2021.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Emmanuel Abruzzo
 * @date 06/01/2022
 */
@Controller
public class ErrorController {

    private static Logger logger = LoggerFactory.getLogger(ErrorController.class);

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @GetMapping("error")
    public String exception(final Throwable throwable, final Model model) {
        logger.error("Ocorreu o seguinte erro durante a execução da aplicação", throwable);
        String errorMessage = (throwable != null ? throwable.getStackTrace().toString() : "Erro desconhecido");
        if(!model.containsAttribute("errorMessage"))
            model.addAttribute("errorMessage", errorMessage);
        return "error";
    }

}
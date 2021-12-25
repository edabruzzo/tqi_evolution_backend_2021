package br.com.abruzzo.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/cliente")
public class ClienteController {

    @RequestMapping(method= RequestMethod.POST)
    public void cadastraCliente(@ResponseBody){

    }


}

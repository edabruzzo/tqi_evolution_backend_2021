package br.com.abruzzo.controller;


import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequestMapping("/cliente")
public class ClienteController {

    private static final Logger logger = LoggerFactory.getLogger(ClienteController.class);

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Cliente> getHeroById(@PathVariable String id) {
        logger.info("Chegou GET request no Endpoint {}/{}/{}", ParametrosConfig.ENDPOINT_BASE.getValue()
                , ParametrosConfig.HEROES_ENDPOINT.getValue()
                ,id);
        Mono<Hero> hero = heroService.findById(id);
        HttpStatus status = (hero != null) ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(hero,status);
    }


}

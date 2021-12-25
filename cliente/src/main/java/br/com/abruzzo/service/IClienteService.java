package br.com.abruzzo.service;

import br.com.abruzzo.model.Cliente;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Emmanuel Abruzzo
 * @date 25/12/2021
 */
public interface IClienteService {

    Flux<Cliente> findAll();

    Mono<Cliente> findById(String id);

    Mono<Cliente> save(Cliente Cliente);

    void deleteById(String id);
}

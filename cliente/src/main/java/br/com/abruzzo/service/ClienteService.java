package br.com.abruzzo.service;

import br.com.abruzzo.model.Cliente;
import br.com.abruzzo.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ClienteService implements IClienteService {

        @Autowired
        private ClienteRepository clienteRepository;

        @Override
        public Flux<Cliente> findAll() {return Flux.fromIterable(clienteRepository.findAll());}
        @Override
        public Mono<Cliente> findById(String id) {return Mono.justOrEmpty(clienteRepository.findById(id));}
        @Override
        public Mono<Cliente> save(Cliente Cliente) {return Mono.justOrEmpty(clienteRepository.save(Cliente));}
        @Override
        public void deleteById(String id) {clienteRepository.deleteById(id);}
}

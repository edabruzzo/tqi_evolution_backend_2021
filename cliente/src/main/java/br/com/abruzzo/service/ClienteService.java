package br.com.abruzzo.service;

import br.com.abruzzo.model.Cliente;
import br.com.abruzzo.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

        @Autowired
        private ClienteRepository clienteRepository;

        public List<Cliente> findAll() {return clienteRepository.findAll();}

        public Optional<Cliente> findById(Long id) {return clienteRepository.findById(id);}

        public Cliente save(Cliente cliente){return clienteRepository.save(cliente);}

        public void deleteById(Long id) {clienteRepository.deleteById(id);}
}

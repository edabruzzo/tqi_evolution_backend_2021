package br.com.abruzzo.service;

import br.com.abruzzo.model.Cliente;
import br.com.abruzzo.repository.ClienteRepository;
import br.com.abruzzo.utils.Utils;
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


        public Cliente save(Cliente cliente){
           boolean cpfValido = validarCPF(cliente.getCpf());
           if (cpfValido) return clienteRepository.save(cliente);
           else return null;
        }

        private boolean validarCPF(String cpf) {

            return Utils.validarCPF(cpf);
        }

        public void deleteById(Long id) {clienteRepository.deleteById(id);}
}

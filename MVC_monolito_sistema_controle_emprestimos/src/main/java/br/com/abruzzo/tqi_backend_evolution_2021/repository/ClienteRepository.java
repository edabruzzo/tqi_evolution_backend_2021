package br.com.abruzzo.tqi_backend_evolution_2021.repository;

import br.com.abruzzo.tqi_backend_evolution_2021.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Emmanuel Abruzzo
 * @date 06/01/2022
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Cliente findByCpf(String cpf);
}

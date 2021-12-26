package br.com.abruzzo.repository;

import br.com.abruzzo.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * @author Emmanuel Abruzzo
 * @date 25/12/2021
 */

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> { }
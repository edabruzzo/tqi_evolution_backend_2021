package br.com.abruzzo.repository;

import br.com.abruzzo.model.Cliente;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;


/**
 * @author Emmanuel Abruzzo
 * @date 25/12/2021
 */
@EnableScan
public interface ClienteRepository extends CrudRepository<Cliente, String> { }
package br.com.abruzzo.emprestimo.repository;

import br.com.abruzzo.emprestimo.model.Emprestimo;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * @author Emmanuel Abruzzo
 * @date 26/12/2021
 */

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {}
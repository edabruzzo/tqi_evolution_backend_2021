package br.com.abruzzo.repository;


import br.com.abruzzo.model.SolicitacaoEmprestimo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Emmanuel Abruzzo
 * @date 29/12/2021
 */
@Repository
public interface ServicoSolicitacaoEmprestimoRepository extends CrudRepository<SolicitacaoEmprestimo, Long> {


    Optional<SolicitacaoEmprestimo> findByIdCliente(Long idCliente);
    Optional<SolicitacaoEmprestimo> findByCpfCliente(String cpfCliente);



}
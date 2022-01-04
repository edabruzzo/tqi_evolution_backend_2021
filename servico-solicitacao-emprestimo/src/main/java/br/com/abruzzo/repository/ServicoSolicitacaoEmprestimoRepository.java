package br.com.abruzzo.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Emmanuel Abruzzo
 * @date 29/12/2021
 */
@Repository
public interface ServicoSolicitacaoEmprestimoRepository extends CrudRepository<SolicitacaoEmprestimo, Long> {


    Optional<SolicitacaoEmprestimo> findByIdCliente(Long idCliente);
    Optional<SolicitacaoEmprestimo> findByCpfCliente(String cpfCliente);
    List<SolicitacaoEmprestimo> findAllByIdCliente(Long idCliente);
    List<SolicitacaoEmprestimo> findAllByCpfCliente(String cpfCliente);


}
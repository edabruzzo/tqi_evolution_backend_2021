package br.com.abruzzo.repository;


import br.com.abruzzo.model.SolicitacaoEmprestimo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Emmanuel Abruzzo
 * @date 29/12/2021
 */
@Repository
public interface ServicoSolicitacaoEmprestimoRepository extends CrudRepository<SolicitacaoEmprestimo, Integer> {

    SolicitacaoEmprestimo findBySolicitacaoEmprestimoById(Integer customerId);

}
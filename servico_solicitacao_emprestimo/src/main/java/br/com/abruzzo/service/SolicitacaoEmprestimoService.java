package br.com.abruzzo.service;

import br.com.abruzzo.model.SolicitacaoEmprestimo;
import br.com.abruzzo.repository.ServicoSolicitacaoEmprestimoRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Emmanuel Abruzzo
 * @date 29/12/2021
 */
public class SolicitacaoEmprestimoService {


    ServicoSolicitacaoEmprestimoRepository solicitacaoEmprestimoRepository;

    public SolicitacaoEmprestimoService(ServicoSolicitacaoEmprestimoRepository solicitacaoEmprestimoRepository) {
        this.solicitacaoEmprestimoRepository = solicitacaoEmprestimoRepository;
    }


    public List<SolicitacaoEmprestimo> findAll() {return (List<SolicitacaoEmprestimo>) solicitacaoEmprestimoRepository.findAll();}

    public Optional<SolicitacaoEmprestimo> findById(Long id) {return solicitacaoEmprestimoRepository.findById(id);}


    public SolicitacaoEmprestimo save(SolicitacaoEmprestimo emprestimo){
        return solicitacaoEmprestimoRepository.save(emprestimo);
    }


}

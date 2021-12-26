package br.com.abruzzo.service;

import br.com.abruzzo.model.Emprestimo;
import br.com.abruzzo.repository.EmprestimoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Emmanuel Abruzzo
 * @date 26/12/2021
 */
@Service
public class EmprestimoService {



    private EmprestimoRepository emprestimoRepository;


    public EmprestimoService(EmprestimoRepository emprestimoRepository) {
        this.emprestimoRepository = emprestimoRepository;
    }

    public List<Emprestimo> findAll() {return emprestimoRepository.findAll();}

    public Optional<Emprestimo> findById(Long id) {return emprestimoRepository.findById(id);}


    public Emprestimo save(Emprestimo emprestimo){
       return emprestimoRepository.save(emprestimo);
    }

    public void deleteById(Long id) {emprestimoRepository.deleteById(id);}
}

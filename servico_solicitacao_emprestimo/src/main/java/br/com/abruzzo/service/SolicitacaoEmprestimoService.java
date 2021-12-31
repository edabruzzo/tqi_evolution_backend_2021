package br.com.abruzzo.service;

import br.com.abruzzo.avaliacao_emprestimos.Avaliacao;
import br.com.abruzzo.exceptions.ErroOperacaoTransacionalBancoException;
import br.com.abruzzo.model.SolicitacaoEmprestimo;
import br.com.abruzzo.model.SolicitacaoEmprestimoStatus;
import br.com.abruzzo.repository.ServicoSolicitacaoEmprestimoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

/**
 * @author Emmanuel Abruzzo
 * @date 29/12/2021
 */
public class SolicitacaoEmprestimoService {

    private static final Logger logger = LoggerFactory.getLogger(SolicitacaoEmprestimoService.class);

    ServicoSolicitacaoEmprestimoRepository solicitacaoEmprestimoRepository;

    public SolicitacaoEmprestimoService(ServicoSolicitacaoEmprestimoRepository solicitacaoEmprestimoRepository) {
        this.solicitacaoEmprestimoRepository = solicitacaoEmprestimoRepository;
    }

    @Autowired
    IntercomunicacaoServicoGerenciamentoEmprestimosAprovados intercomunicacaoServicoGerenciamentoProcesso;

    @Autowired
    IntercomunicacaoServicoEnvioEmailClientes intercomunicacaoServicoEnvioEmailClientes;



    public List<SolicitacaoEmprestimo> findAll() {return (List<SolicitacaoEmprestimo>) solicitacaoEmprestimoRepository.findAll();}

    public Optional<SolicitacaoEmprestimo> findById(Long id) {return solicitacaoEmprestimoRepository.findById(id);}


    public SolicitacaoEmprestimo save(SolicitacaoEmprestimo solicitacaoEmprestimo){

        solicitacaoEmprestimo.setStatus(String.valueOf(SolicitacaoEmprestimoStatus.EM_PROCESSAMENTO));
        try{

            solicitacaoEmprestimo = solicitacaoEmprestimoRepository.save(solicitacaoEmprestimo);
            logger.info("Solicitação de empréstimo salva");
            logger.info("{}", solicitacaoEmprestimo);

        }catch(Exception erro){

            solicitacaoEmprestimo.setStatus(String.valueOf(SolicitacaoEmprestimoStatus.PROBLEMA_AO_SALVAR));

            String mensagem = "Erro ao salvar a solicitação de empréstimno em banco";
            try {
                throw new ErroOperacaoTransacionalBancoException(mensagem, logger);
            } catch (ErroOperacaoTransacionalBancoException e) {
                e.printStackTrace();
            }
        }

        solicitacaoEmprestimo.setStatus(String.valueOf(SolicitacaoEmprestimoStatus.EM_AVALIACAO));

        boolean solicitacaoEmprestimoAprovada = Avaliacao.enviarParaProcessamento(solicitacaoEmprestimo);

        if(solicitacaoEmprestimoAprovada){

            solicitacaoEmprestimo.setStatus(String.valueOf(SolicitacaoEmprestimoStatus.APROVADA));

            this.intercomunicacaoServicoEnvioEmailClientes.enviarEmailAoCliente(solicitacaoEmprestimo);

            logger.info(String.format("Empréstimo aprovado -> {}",solicitacaoEmprestimo));

            logger.info("Encaminhando solicitação de empréstimo aprovada para o serviço de gerenciamento de empréstimos");

             this.intercomunicacaoServicoGerenciamentoProcesso.cadastrarEmprestimoAprovadoServicoGerenciamentoEmprestimo(solicitacaoEmprestimo);

             solicitacaoEmprestimo.setStatus(String.valueOf(SolicitacaoEmprestimoStatus.CONSOLIDADA));

        }else{
            solicitacaoEmprestimo.setStatus(String.valueOf(SolicitacaoEmprestimoStatus.NAO_AUTORIZADA));
            logger.info(String.format("Empréstimo reprovado -> {}",solicitacaoEmprestimo));
        }

        solicitacaoEmprestimoRepository.save(solicitacaoEmprestimo);
        return solicitacaoEmprestimo;
    }


    public Optional<SolicitacaoEmprestimo> verificarSolicitacaoEmprestimo(Long idSolicitacaoEmprestimo, Long idCliente, String cpfCliente) {

        if(idSolicitacaoEmprestimo != null)
            return this.findById(idSolicitacaoEmprestimo);
        else if(idCliente != null)
            return this.solicitacaoEmprestimoRepository.findByIdCliente(idCliente);
        else 
            return this.solicitacaoEmprestimoRepository.findByCpfCliente(cpfCliente);

    }


    public List<SolicitacaoEmprestimo> verificarSolicitacoesEmprestimoCliente(Long idCliente, String cpfCliente) {

        if(idCliente != null)
            return this.solicitacaoEmprestimoRepository.findAllByIdCliente(idCliente);
        else
            return this.solicitacaoEmprestimoRepository.findAllByCpfCliente(cpfCliente);
    }

}

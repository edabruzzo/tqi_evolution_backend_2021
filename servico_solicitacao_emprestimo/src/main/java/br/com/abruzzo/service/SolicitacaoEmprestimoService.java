package br.com.abruzzo.service;

import br.com.abruzzo.avaliacao_emprestimos.Avaliacao;
import br.com.abruzzo.exceptions.ErroOperacaoTransacionalBancoException;
import br.com.abruzzo.model.SolicitacaoEmprestimo;
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
    IntercomunicacaoServicoGerenciamentoProcesso intercomunicacaoServicoGerenciamentoProcesso;

    @Autowired
    IntercomunicacaoServicoEnvioEmailClientes intercomunicacaoServicoEnvioEmailClientes;



    public List<SolicitacaoEmprestimo> findAll() {return (List<SolicitacaoEmprestimo>) solicitacaoEmprestimoRepository.findAll();}

    public Optional<SolicitacaoEmprestimo> findById(Long id) {return solicitacaoEmprestimoRepository.findById(id);}


    public SolicitacaoEmprestimo save(SolicitacaoEmprestimo solicitacaoEmprestimo){

        SolicitacaoEmprestimo solicitacaoEmprestimoSalva = new SolicitacaoEmprestimo();

        solicitacaoEmprestimo.setStatus("Em processamento");
        try{

            solicitacaoEmprestimoSalva = solicitacaoEmprestimoRepository.save(solicitacaoEmprestimo);
            logger.info("Solicitação de empréstimo salva");
            logger.info("{}", solicitacaoEmprestimoSalva);

        }catch(Exception erro){
            String mensagem = "Erro ao salvar a solicitação de empréstimno em banco";
            try {
                throw new ErroOperacaoTransacionalBancoException(mensagem, logger);
            } catch (ErroOperacaoTransacionalBancoException e) {
                e.printStackTrace();
            }
        }

        boolean emprestimoAprovado = Avaliacao.enviarParaProcessamento(solicitacaoEmprestimoSalva);

        if(emprestimoAprovado){

            this.intercomunicacaoServicoEnvioEmailClientes.enviarEmailAoCliente(solicitacaoEmprestimoSalva);
            solicitacaoEmprestimo.setStatus("Aprovado");
            logger.info(String.format("Empréstimo aprovado -> {}",solicitacaoEmprestimoSalva));
            logger.info("Encaminhando solicitação de empréstimo aprovada para o serviço de gerenciamento de empréstimos");

             this.intercomunicacaoServicoGerenciamentoProcesso.cadastrarEmprestimoAprovadoServicoGerenciamentoEmprestimo(solicitacaoEmprestimoSalva);

        }else{
            solicitacaoEmprestimoSalva.setStatus("Reprovado");
            logger.info(String.format("Empréstimo reprovado -> {}",solicitacaoEmprestimoSalva));
        }

        solicitacaoEmprestimoRepository.save(solicitacaoEmprestimoSalva);
        return solicitacaoEmprestimoSalva;

    }


    public Optional<SolicitacaoEmprestimo> verificarSolicitacaoEmprestimo(Long idSolicitacaoEmprestimo, Long idCliente, String cpfCliente) {

        if(idSolicitacaoEmprestimo != null)
            return this.findById(idSolicitacaoEmprestimo);
        else if(idCliente != null)
            return this.solicitacaoEmprestimoRepository.findByIdCliente(idCliente);
        else 
            return this.solicitacaoEmprestimoRepository.findByCpfCliente(cpfCliente);

    }

}
package br.com.abruzzo.tqi_backend_evolution_2021.service;

/**
 * @author Emmanuel Abruzzo
 * @date 06/01/2022
 */

import br.com.abruzzo.tqi_backend_evolution_2021.dto.EmprestimoDTO;
import br.com.abruzzo.tqi_backend_evolution_2021.dto.SolicitacaoClienteEmprestimoDTO;
import br.com.abruzzo.tqi_backend_evolution_2021.dto.StatusEmprestimoDTO;
import br.com.abruzzo.tqi_backend_evolution_2021.exceptions.ErroOperacaoTransacionalBancoException;
import br.com.abruzzo.tqi_backend_evolution_2021.model.Cliente;
import br.com.abruzzo.tqi_backend_evolution_2021.model.Emprestimo;
import br.com.abruzzo.tqi_backend_evolution_2021.repository.EmprestimoRepository;
import br.com.abruzzo.tqi_backend_evolution_2021.util.VerificacoesSessao;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmprestimoService {


    private static final Logger logger = LoggerFactory.getLogger(EmprestimoService.class);


    @Autowired
    EmprestimoRepository emprestimoRepository;

    @Autowired
    SolicitacaoEmprestimoValidacoesService solicitacaoEmprestimoValidacoesService;


    @Autowired
    ModelMapper modelMapper;



    public List<EmprestimoDTO> retornaTodosEmprestimos() {


        boolean usuarioLogadoCliente = VerificacoesSessao.verificaSeUsuarioLogadoCliente();

        String emailCliente = VerificacoesSessao.verificaEmailUsuarioLogado();

        List<Emprestimo> listaEmprestimos = new ArrayList<>();

        if(usuarioLogadoCliente)
            listaEmprestimos = this.emprestimoRepository.findAllByEmail(emailCliente);
        else
            listaEmprestimos = this.emprestimoRepository.findAll();

        List<EmprestimoDTO> listaEmprestimosDTO = this.converterListaEmprestimoModelToListaEmprestimoDTO(listaEmprestimos);

        return listaEmprestimosDTO;
    }


    private List<EmprestimoDTO> converterListaEmprestimoModelToListaEmprestimoDTO(List<Emprestimo> listaEmprestimos) {

        List<EmprestimoDTO> listaEmprestimoDTO = new ArrayList<>();

        listaEmprestimos.stream().forEach(emprestimo ->{

            EmprestimoDTO emprestimoDTO = converteEmprestimoModelToDTO(emprestimo);
            listaEmprestimoDTO.add(emprestimoDTO);

        });
        return listaEmprestimoDTO;
    }


    public EmprestimoDTO criarEmprestimo(SolicitacaoClienteEmprestimoDTO solicitacaoClienteEmprestimoDTO, Cliente cliente) {

        logger.info("Requisição para solicitar um emprestimo %s",solicitacaoClienteEmprestimoDTO);

        EmprestimoDTO emprestimoDTO = new EmprestimoDTO();
        emprestimoDTO.setStatusEmprestimoDTO(StatusEmprestimoDTO.ABERTO);
        emprestimoDTO.setDataSolicitacao(LocalDateTime.now());
        emprestimoDTO.setDataPrimeiraParcela(solicitacaoClienteEmprestimoDTO.getDataPrimeiraParcela());
        emprestimoDTO.setCliente(cliente);

        emprestimoDTO = this.solicitacaoEmprestimoValidacoesService.validarSolicitacaoEmprestimo(emprestimoDTO);


        try {
            Emprestimo emprestimo = converteEmprestimoDTOToEmprestimoModel(emprestimoDTO);
            Emprestimo emprestimoSalvo = this.emprestimoRepository.save(emprestimo);
            emprestimoDTO = converteEmprestimoModelToDTO(emprestimoSalvo);
            logger.info("Solicitação de empréstimo salva %s",emprestimoDTO);

        }catch(Exception erro){

            emprestimoDTO.setStatusEmprestimoDTO(StatusEmprestimoDTO.PROBLEMA_AO_SALVAR);

            String mensagemErro = String.format("Erro ao salvar solicitação de empréstimo %s",emprestimoDTO);

            throw new ErroOperacaoTransacionalBancoException(mensagemErro,logger);

        }finally{
            return emprestimoDTO;
        }

    }


    private EmprestimoDTO converteEmprestimoModelToDTO(Emprestimo emprestimo) {
        EmprestimoDTO emprestimoDTO = this.modelMapper.map(emprestimo,EmprestimoDTO.class);
        return emprestimoDTO;
    }

    private Emprestimo converteEmprestimoDTOToEmprestimoModel(EmprestimoDTO emprestimoDTO) {
        Emprestimo emprestimo = this.modelMapper.map(emprestimoDTO, Emprestimo.class);
        return emprestimo;
    }

    public List<EmprestimoDTO> retornaTodosEmprestimosByCliente(String cpfCliente) {

        List<Emprestimo> listaEmprestimos = this.emprestimoRepository.findAllByCpf(cpfCliente);
        List<EmprestimoDTO> listaEmprestimosDTO = this.converterListaEmprestimoModelToListaEmprestimoDTO(listaEmprestimos);
        return listaEmprestimosDTO;
    }


    public List<EmprestimoDTO> retornaTodosEmprestimosByEmailCliente(String emailCliente) {

        List<Emprestimo> listaEmprestimos = this.emprestimoRepository.findAllByEmail(emailCliente);
        List<EmprestimoDTO> listaEmprestimosDTO = this.converterListaEmprestimoModelToListaEmprestimoDTO(listaEmprestimos);
        return listaEmprestimosDTO;
    }


}

package br.com.abruzzo.tqi_backend_evolution_2021.service;

import br.com.abruzzo.tqi_backend_evolution_2021.dto.EmprestimoDTO;
import br.com.abruzzo.tqi_backend_evolution_2021.dto.StatusEmprestimoDTO;
import br.com.abruzzo.tqi_backend_evolution_2021.exceptions.AutorizacaoException;
import br.com.abruzzo.tqi_backend_evolution_2021.exceptions.BusinessExceptionClienteNaoCadastrado;
import br.com.abruzzo.tqi_backend_evolution_2021.exceptions.BusinessExceptionCondicoesEmprestimoIrregulares;
import br.com.abruzzo.tqi_backend_evolution_2021.exceptions.BusinessExceptionCondicoesIrregularesCliente;
import br.com.abruzzo.tqi_backend_evolution_2021.model.Cliente;
import br.com.abruzzo.tqi_backend_evolution_2021.validacoes.Avaliacao;
import br.com.abruzzo.tqi_backend_evolution_2021.validacoes.ValidacoesCliente;
import br.com.abruzzo.tqi_backend_evolution_2021.validacoes.ValidacoesEmprestimo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Emmanuel Abruzzo
 * @date 06/01/2022
 */
@Service
public class SolicitacaoEmprestimoValidacoesService {

    private static final Logger logger = LoggerFactory.getLogger(SolicitacaoEmprestimoValidacoesService.class);


    @Autowired
    IntercomunicacaoServicoEnvioEmailClientes intercomunicacaoServicoEnvioEmailClientes;

    @Autowired
    ClienteService clienteService;


    public EmprestimoDTO validarSolicitacaoEmprestimo(EmprestimoDTO emprestimoDTO){

        emprestimoDTO.setStatusEmprestimoDTO(StatusEmprestimoDTO.EM_PROCESSAMENTO);

        boolean clientePodePedirEmprestimo = validarSeClientePodePedirEmprestimo(emprestimoDTO);
        boolean condicoesEmprestimoRegulares = validarCondicoesEmprestimo(emprestimoDTO);


        if( !clientePodePedirEmprestimo || ! condicoesEmprestimoRegulares){

            emprestimoDTO.setStatusEmprestimoDTO(StatusEmprestimoDTO.NAO_AUTORIZADO);

            String mensagemErro = String.format("Solicitação de Empréstimo não autorizada: %s\n",emprestimoDTO);

            if(!clientePodePedirEmprestimo)
                mensagemErro += "Cliente não pode pedir empréstimo\n";

            if(!condicoesEmprestimoRegulares)
                mensagemErro += "Condições empréstimo irregulares\n";


            try {

                throw new AutorizacaoException(mensagemErro,logger);

            } catch (AutorizacaoException e) {
                e.printStackTrace();
            }finally{
                return emprestimoDTO;
            }
        }



        if(clientePodePedirEmprestimo && condicoesEmprestimoRegulares){

            emprestimoDTO.setStatusEmprestimoDTO(StatusEmprestimoDTO.EM_AVALIACAO);

            boolean solicitacaoEmprestimoAprovada = Avaliacao.enviarParaProcessamento(emprestimoDTO);

            if(solicitacaoEmprestimoAprovada)
                emprestimoDTO.setStatusEmprestimoDTO(StatusEmprestimoDTO.APROVADO);
            else
                emprestimoDTO.setStatusEmprestimoDTO(StatusEmprestimoDTO.NAO_APROVADO);
        }


        return emprestimoDTO;

    }


    private boolean validarSeClientePodePedirEmprestimo(EmprestimoDTO emprestimoDTO) {

        boolean condicoesRegulares = false;

        Cliente cliente = this.clienteService.findByCPF(emprestimoDTO.getCliente().getCpf());

        if (cliente == null){

            try {
                String mensagemErro = "Foi feita solicitação de empréstimo para um cliente não cadastrado";
                throw new BusinessExceptionClienteNaoCadastrado(mensagemErro,logger);
            } catch (BusinessExceptionClienteNaoCadastrado e) {
                e.printStackTrace();
                return condicoesRegulares;
            }

        }else{

            boolean validacao1 = ValidacoesCliente.cpfValido(cliente.getCpf());
            boolean validacao2 = ValidacoesCliente.rgValido(cliente.getRg());
            boolean validacao3 = ValidacoesCliente.margemRendaCompativelComValorParcela(emprestimoDTO);

            condicoesRegulares = validacao1 && validacao2 && validacao3;

            if(!condicoesRegulares){
                String mensagemErro = "";
                if(!validacao1)
                    mensagemErro += String.format("CPF do cliente inválido [%s]\n",cliente);
                if(!validacao2)
                    mensagemErro += String.format("RG do cliente inválido [%s]\n",cliente);
                if(!validacao3)
                    mensagemErro += String.format("Renda do cliente [%s] incompatível com valor do empréstimo: %.2f \n",cliente, emprestimoDTO.getValor());

                try {
                    throw new BusinessExceptionCondicoesIrregularesCliente(mensagemErro,logger);
                } catch (BusinessExceptionCondicoesIrregularesCliente e) {
                    e.printStackTrace();
                }
            }

            return condicoesRegulares;

        }
    }



    private boolean validarCondicoesEmprestimo(EmprestimoDTO emprestimoDTO) {

        boolean condicoesRegulares = false;
        boolean validacao1 = ValidacoesEmprestimo.validarDataPrimeiraParcela(emprestimoDTO.getDataPrimeiraParcela());
        boolean validacao2 = ValidacoesEmprestimo.validarNumeroParcelas(emprestimoDTO.getNumeroMaximoParcelas());

        condicoesRegulares = validacao1 && validacao2;
        String mensagemErro = "";
        if(!condicoesRegulares){
            if(!validacao1) mensagemErro += String.format("A data da primeira parcela escolhida ({}) é superior a 3 meses",emprestimoDTO.getDataPrimeiraParcela());
            if(!validacao1) mensagemErro += String.format("O número de parcelas escolhido ({}) é superior a 60 parcelas",emprestimoDTO.getDataPrimeiraParcela());

            try {
                throw new BusinessExceptionCondicoesEmprestimoIrregulares(mensagemErro, logger);
            } catch (BusinessExceptionCondicoesEmprestimoIrregulares e) {
                e.printStackTrace();
            }
        }

        return condicoesRegulares;

    }


}

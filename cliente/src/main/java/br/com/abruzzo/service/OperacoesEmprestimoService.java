package br.com.abruzzo.service;

import br.com.abruzzo.config.ParametrosConfig;
import br.com.abruzzo.dto.EmprestimoDTO;
import br.com.abruzzo.exceptions.BusinessExceptionClienteNaoCadastrado;
import br.com.abruzzo.exceptions.BusinessExceptionCondicoesEmprestimoIrregulares;
import br.com.abruzzo.exceptions.BusinessExceptionCondicoesIrregularesCliente;
import br.com.abruzzo.model.Cliente;
import br.com.abruzzo.validacoes.ValidacoesCliente;
import br.com.abruzzo.validacoes.ValidacoesEmprestimo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.sql.Date;
import java.util.Optional;

/**
 *
 * @link https://www.baeldung.com/spring-resttemplate-post-json
 * @author Emmanuel Abruzzo
 * @date 26/12/2021
 */
@Service
public class OperacoesEmprestimoService {

    private static final Logger logger = LoggerFactory.getLogger(OperacoesEmprestimoService.class);

    private final URI urlSolicitacaoEmprestimo = URI.create(ParametrosConfig.OPERACAO_EMPRESTIMO_ENDPOINT.getValue());

    private ClienteService clienteService;

    public OperacoesEmprestimoService(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    public ResponseEntity<String> solicitarEmprestimo(Long idCliente, double valor, int parcelas, Date dataPrimeiraParcela) {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        EmprestimoDTO emprestimoDTO = new EmprestimoDTO();
        emprestimoDTO.setIdCliente(idCliente);
        emprestimoDTO.setValor(valor);
        emprestimoDTO.setNumeroMaximoParcelas(parcelas);
        emprestimoDTO.setData_primeira_parcela(dataPrimeiraParcela);

        ResponseEntity resultado =  ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        boolean clientePodePedirEmprestimo = validarSeClientePodePedirEmprestimo(idCliente, valor, parcelas);
        boolean condicoesEmprestimoRegulares = validarCondicoesEmprestimo(parcelas, dataPrimeiraParcela);

        if(clientePodePedirEmprestimo && condicoesEmprestimoRegulares)
            return restTemplate.postForEntity(this.urlSolicitacaoEmprestimo, emprestimoDTO, String.class);
        else
            return resultado;

        }


    private boolean validarCondicoesEmprestimo(int parcelas, Date dataPrimeiraParcela) {

        boolean condicoesRegulares = false;
        boolean validacao1 = ValidacoesEmprestimo.validarDataPrimeiraParcela(dataPrimeiraParcela);
        boolean validacao2 = ValidacoesEmprestimo.validarNumeroParcelas(parcelas);

        condicoesRegulares = validacao1 && validacao2;
        String mensagemErro = "";
        if(!condicoesRegulares){
            if(!validacao1) mensagemErro += String.format("A data da primeira parcela escolhida ({}) é superior a 3 meses",dataPrimeiraParcela);
            if(!validacao1) mensagemErro += String.format("O número de parcelas escolhido ({}) é superior a 60 parcelas",dataPrimeiraParcela);

            try {
                throw new BusinessExceptionCondicoesEmprestimoIrregulares(mensagemErro, logger);
            } catch (BusinessExceptionCondicoesEmprestimoIrregulares e) {
                e.printStackTrace();
            }
        }

        return condicoesRegulares;

    }

    private boolean validarSeClientePodePedirEmprestimo(Long idCliente, double valor, int numeroParcelas) {

        boolean condicoesRegulares = false;

        Optional<Cliente> cliente = this.clienteService.findById(idCliente);
        if (cliente.isEmpty()){

            try {
                String mensagemErro = "Foi feita solicitação de empréstimo para um cliente não cadastrado";
                throw new BusinessExceptionClienteNaoCadastrado(mensagemErro,logger);
            } catch (BusinessExceptionClienteNaoCadastrado e) {
                e.printStackTrace();
                return condicoesRegulares;
            }

        }else{

            boolean validacao1 = ValidacoesCliente.cpfValido(cliente.get().getCpf());
            boolean validacao2 = ValidacoesCliente.rgValido(cliente.get().getRg());
            boolean validacao3 = ValidacoesCliente.margemRendaCompativelComValorParcela(cliente.get().getRenda(),valor,numeroParcelas);

            condicoesRegulares = validacao1 && validacao2 && validacao3;

            if(!condicoesRegulares){
                String mensagemErro = "";
                if(!validacao1)
                    mensagemErro += String.format("CPF ({}) do cliente de id {} inválido \n",cliente.get().getCpf(),cliente.get().getId());
                if(!validacao2)
                    mensagemErro += String.format("RG ({}) do cliente de id {} inválido \n",cliente.get().getCpf(),cliente.get().getId());
                if(!validacao3)
                    mensagemErro += String.format("Renda ({}) do cliente de id {} incompatível com valor do empréstimo: {} \n",cliente.get().getRenda(),cliente.get().getId(),valor);

                try {
                    throw new BusinessExceptionCondicoesIrregularesCliente(mensagemErro,logger);
                } catch (BusinessExceptionCondicoesIrregularesCliente e) {
                    e.printStackTrace();
                }
            }

            return condicoesRegulares;

        }

    }


}

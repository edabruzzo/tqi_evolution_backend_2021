package br.com.abruzzo.service;

import br.com.abruzzo.config.ParametrosConfig;
import br.com.abruzzo.dto.EmprestimoDTO;
import br.com.abruzzo.model.Cliente;
import br.com.abruzzo.validacoes.ValidacoesCliente;
import br.com.abruzzo.validacoes.ValidacoesEmprestimo;
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

    private URI urlSolicitacaoEmprestimo = URI.create(ParametrosConfig.OPERACAO_EMPRESTIMO_ENDPOINT.getValue());

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

        ResponseEntity<String> resultado = ResponseEntity.unprocessableEntity().build();

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

        return condicoesRegulares;

    }

    private boolean validarSeClientePodePedirEmprestimo(Long idCliente, double valor, int numeroParcelas) {

        boolean condicoesRegulares = false;

        Optional<Cliente> cliente = this.clienteService.findById(idCliente);

        boolean validacao1 = ValidacoesCliente.cpfValido(cliente.get().getCpf());
        boolean validacao2 = ValidacoesCliente.rgValido(cliente.get().getRg());
        boolean validacao3 = ValidacoesCliente.margemRendaCompativelComValorParcela(cliente.get().getRenda(),valor,numeroParcelas);

        condicoesRegulares = validacao1 && validacao2 && validacao3;

        return condicoesRegulares;

    }


}

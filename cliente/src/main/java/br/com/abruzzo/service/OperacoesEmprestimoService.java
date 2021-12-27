package br.com.abruzzo.service;

import br.com.abruzzo.config.ParametrosConfig;
import br.com.abruzzo.dto.EmprestimoDTO;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.sql.Date;

/**
 *
 * @link https://www.baeldung.com/spring-resttemplate-post-json
 * @author Emmanuel Abruzzo
 * @date 26/12/2021
 */
@Service
public class OperacoesEmprestimoService {

    private URI urlSolicitacaoEmprestimo = URI.create(ParametrosConfig.OPERACAO_EMPRESTIMO_ENDPOINT.getValue());


    public ResponseEntity<String> solicitarEmprestimo(int idCliente, double valor, int parcelas, Date dataPrimeiraParcela) {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        EmprestimoDTO emprestimoDTO = new EmprestimoDTO();
        emprestimoDTO.setIdCliente(idCliente);
        emprestimoDTO.setValor(valor);
        emprestimoDTO.setNumeroMaximoParcelas(parcelas);
        emprestimoDTO.setData_primeira_parcela(dataPrimeiraParcela);

        ResponseEntity<String> resultado = restTemplate.postForEntity(this.urlSolicitacaoEmprestimo, emprestimoDTO, String.class);

        return resultado;

        }


}

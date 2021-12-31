package br.com.abruzzo.service;

import br.com.abruzzo.client.ServicoEmprestimoFeignClient;
import br.com.abruzzo.config.ParametrosConfig;
import br.com.abruzzo.dto.EmprestimoDTO;
import br.com.abruzzo.exceptions.InfraStructrutureException;
import br.com.abruzzo.model.SolicitacaoEmprestimo;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

/**
 * @link https://howtodoinjava.com/spring-boot2/resttemplate/resttemplate-post-json-example/
 * @link https://www.baeldung.com/spring-resttemplate-post-json
 *
 * @author Emmanuel Abruzzo
 * @date 29/12/2021
 */
@Service
public class IntercomunicacaoServicoGerenciamentoEmprestimosAprovados {


    private static final Logger logger = LoggerFactory.getLogger(IntercomunicacaoServicoGerenciamentoEmprestimosAprovados.class);

    @Autowired
    private EurekaClient eurekaDiscoveryClient;

    @Autowired
    private ServicoEmprestimoFeignClient servicoEmprestimoFeignClient;


    /**
     *
     * Neste método é o serviço de gerenciamento de empréstimos aprovados
     * é chamado e um DTO Emprestimo que representa um empréstimo já aprovado
     * é passado via chamada REST com o método POST utilizando a API de
     * gerenciamento de empréstimos já registrada no Eureka Discovery Server
     *
     * @param solicitacaoEmprestimoSalva
     * @return
     */
    @HystrixCommand(fallbackMethod = "solicitaEmprestimoFallback",
    threadPoolKey = "cadastrarEmprestimoAprovadoServicoGerenciamentoEmprestimo_ThreadPoolemman")
    public ResponseEntity<EmprestimoDTO> cadastrarEmprestimoAprovadoServicoGerenciamentoEmprestimo(SolicitacaoEmprestimo solicitacaoEmprestimoSalva) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        EmprestimoDTO emprestimoDTO = new EmprestimoDTO();
        emprestimoDTO.setIdCliente(solicitacaoEmprestimoSalva.getIdCliente());
        emprestimoDTO.setValor(solicitacaoEmprestimoSalva.getValor());
        emprestimoDTO.setNumeroMaximoParcelas(solicitacaoEmprestimoSalva.getNumeroMaximoParcelas());
        emprestimoDTO.setData_primeira_parcela(solicitacaoEmprestimoSalva.getData_primeira_parcela());

        ResponseEntity resultado = ResponseEntity.status(500).build();

        List<InstanceInfo> listaInstancias = eurekaDiscoveryClient
                .getInstancesByVipAddressAndAppName(null,
                        ParametrosConfig.SERVICO_EMPRESTIMO.getValue(), false);

        listaInstancias.stream().forEach(instancia -> logger.info(instancia.toString()));

        if (eurekaDiscoveryClient.getInstancesByVipAddressAndAppName(null, ParametrosConfig.SERVICO_EMPRESTIMO.getValue(),false).get(0).getStatus().equals(InstanceInfo.InstanceStatus.UP)) {

            try {

                resultado = this.servicoEmprestimoFeignClient.criarEmprestimoConsolidado(emprestimoDTO);

                return resultado;

            }catch (Exception exception){
                String mensagem = String.format("Problema na requisição de cadastro de empréstimo consolidado %s",emprestimoDTO);
                try {
                    throw new InfraStructrutureException(mensagem,logger);
                } catch (InfraStructrutureException e) {
                    e.printStackTrace();
                }
            }

        } else {
            try {
                String mensagemErro = "Serviço de gerenciamento de empréstimos aprovados neste momento está fora do ar";
                throw new InfraStructrutureException(mensagemErro, logger);
            } catch (InfraStructrutureException e) {
                e.printStackTrace();
            }


        }

        return resultado;

    }


    public ResponseEntity<String> solicitarEmprestimoFallback() {
        EmprestimoDTO emprestimoDTOFallback = new EmprestimoDTO();
        return ResponseEntity.ok().body(emprestimoDTOFallback.toString());

    }

}
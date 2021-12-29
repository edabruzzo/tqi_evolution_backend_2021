package br.com.abruzzo.service;

import br.com.abruzzo.config.ParametrosConfig;
import br.com.abruzzo.dto.EmprestimoDTO;
import br.com.abruzzo.exceptions.*;
import br.com.abruzzo.model.Cliente;
import br.com.abruzzo.validacoes.ValidacoesCliente;
import br.com.abruzzo.validacoes.ValidacoesEmprestimo;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.sql.Date;
import java.util.List;
import java.util.Optional;



/**
 *  Classe da camada de serviço responsável por fazer a chamada REST para o microserviço de empréstimos
 *  e por validar a solitação que chegou via chamada Rest no microsserviço responsável por gerenciar clientes.
 *
 *
 *  Necessário importar o @EnableDiscoveryClient da seguinte dependẽncia:
 *  @link org.springframework.cloud.client.discovery.DiscoveryClient
 *  ao invés da seguinte dependência: com.netflix.discovery.DiscoveryClient
 *
 * @link https://stackoverflow.com/questions/42845084/cannot-find-discoveryclient-bean-error-in-spring-boot
 * @link https://spring.io/guides/gs/service-registration-and-discovery/
 * @link https://www.baeldung.com/spring-resttemplate-post-json
 * @link https://www.baeldung.com/spring-cloud-bootstrapping
 * @link https://cloud.spring.io/spring-cloud-netflix/multi/multi__service_discovery_eureka_clients.html
 *
 * @author Emmanuel Abruzzo
 * @date 26/12/2021
 */
@Service
public class OperacoesEmprestimoService {

    private static final Logger logger = LoggerFactory.getLogger(OperacoesEmprestimoService.class);

    private final URI urlSolicitacaoEmprestimo = URI.create(ParametrosConfig.OPERACAO_EMPRESTIMO_ENDPOINT.getValue());

    private ClienteService clienteService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private EurekaClient eurekaDiscoveryClient;

    public OperacoesEmprestimoService(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @HystrixCommand(fallbackMethod = "solicitaEmprestimoFallback")
    public ResponseEntity<String> solicitarEmprestimo(Long idCliente, double valor, int parcelas, Date dataPrimeiraParcela) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        EmprestimoDTO emprestimoDTO = new EmprestimoDTO();
        emprestimoDTO.setIdCliente(idCliente);
        emprestimoDTO.setValor(valor);
        emprestimoDTO.setNumeroMaximoParcelas(parcelas);
        emprestimoDTO.setData_primeira_parcela(dataPrimeiraParcela);
        emprestimoDTO.setCpf(this.clienteService.findById(idCliente).get().getCpf());

        ResponseEntity resultado =  ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        boolean clientePodePedirEmprestimo = validarSeClientePodePedirEmprestimo(idCliente, valor, parcelas);
        boolean condicoesEmprestimoRegulares = validarCondicoesEmprestimo(parcelas, dataPrimeiraParcela);

        if(clientePodePedirEmprestimo && condicoesEmprestimoRegulares){
            List<InstanceInfo> listaInstancias = eurekaDiscoveryClient.getInstancesByVipAddressAndAppName(null,"servico_emprestimo",false);
            listaInstancias.stream().forEach(instancia -> {
                logger.info(String.format("{}:{}",instancia.getHostName(),instancia.getPort()));
            });

            if(eurekaDiscoveryClient.getInstancesByVipAddressAndAppName(null,ParametrosConfig.SERVICO_SOLICITACAO_EMPRESTIMO.getValue(),false)
                    .get(0).getStatus().equals(InstanceInfo.InstanceStatus.UP))
                return restTemplate.postForEntity(this.urlSolicitacaoEmprestimo, emprestimoDTO, String.class);

            else{
                try {
                    throw new InfraStructrutureException("Serviço de solicitação de empréstimo neste momento está forma",logger);
                } catch (InfraStructrutureException e) {
                    e.printStackTrace();
                }
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

        }else{
            try {
                throw new AutorizacaoException(String.format("Empréstimo não autorizado: {}",emprestimoDTO.toString()),logger);
            } catch (AutorizacaoException e) {
                e.printStackTrace();
            }
        }
        return resultado;

        }


    public ResponseEntity<String> solicitarEmprestimoFallback() {
        EmprestimoDTO emprestimoDTOFallback = new EmprestimoDTO();
        return ResponseEntity.ok().body(emprestimoDTOFallback.toString());

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

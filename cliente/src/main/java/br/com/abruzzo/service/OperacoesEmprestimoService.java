package br.com.abruzzo.service;

import br.com.abruzzo.config.ParametrosConfig;
import br.com.abruzzo.dto.SolicitacaoEmprestimoDTO;
import br.com.abruzzo.client.SolicitacaoEmprestimoFeignClient;
import br.com.abruzzo.dto.SolicitacaoEmprestimoStatusDTO;
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


import java.net.URI;
import java.sql.Date;
import java.util.List;
import java.util.Optional;



/**
 *  Classe da camada de serviço responsável por fazer a chamada REST para o microserviço de empréstimos
 *  e por validar a solitação que chegou via chamada Rest no microsserviço responsável por gerenciar clientes.
 *
 *
 *  Optamos por utilizar o Feign como Web Client Declarative Rest para fazer chamadas ao serviço
 *  de solicitação de empréstimos
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
    private EurekaClient eurekaDiscoveryClient;

    @Autowired
    private SolicitacaoEmprestimoFeignClient solicitacaoEmprestimoFeignClient;

    public OperacoesEmprestimoService(ClienteService clienteService) {
        this.clienteService = clienteService;
    }


    /**
     *
     *
     *   Neste método o serviço de solicitação de empréstimos é chamado para persistir
     *    a solicitação de empréstimo em memória, enquanto a solicitação de empréstimo
     *    é avaliada por um sistema de gerenciamento de risco de crédito interno
     *    e são realizadas consultas aos sistemas de proteção ao crédito via chamadas
     *    aos seus respectivos WebServices utilizando o CPF do cliente
     *    um DTO Emprestimo é passado via chamada REST com o método POST utilizando a API de
     *    solicitação de empréstimos já registrada no Eureka Discovery Server,
     *    que irá persistir a solicitação no Redis, enquanto ela está sendo avaliada
     *
     *    Caso a solicitação seja aprovada, o serviço de avaliação da solicitação irá pedir ao
     *    serviço de gerenciamento de empréstimos aprovados que ele persista em sua base de dados
     *    o empréstimo aprovado e, em caso de reprovação, a solicitação tem seu status alterado
     *    para reprovado e apenas permanece em memória durante a existência da aplicação no servidor
     *    que hospeda a aplicação de solicitação de empréstimo
     *
     *
     * @param solicitacaoEmprestimoDTO
     * @return SolicitacaoEmprestimoDTO
     */
    @HystrixCommand(fallbackMethod = "solicitaEmprestimoFallback",
                    threadPoolKey = "solicitarEmprestimoThreadPool")
    public SolicitacaoEmprestimoDTO solicitarEmprestimo(SolicitacaoEmprestimoDTO solicitacaoEmprestimoDTO){

        Long idCliente = solicitacaoEmprestimoDTO.getIdCliente();
        Double valor = solicitacaoEmprestimoDTO.getValor();
        int parcelas = solicitacaoEmprestimoDTO.getNumeroMaximoParcelas();
        Date dataPrimeiraParcela = solicitacaoEmprestimoDTO.getData_primeira_parcela();

        solicitacaoEmprestimoDTO.setCpfCliente(this.clienteService.findById(idCliente).get().getCpf());
        solicitacaoEmprestimoDTO.setEmailCliente(this.clienteService.findById(idCliente).get().getEmail());

        boolean clientePodePedirEmprestimo = validarSeClientePodePedirEmprestimo(idCliente, valor, parcelas);
        boolean condicoesEmprestimoRegulares = validarCondicoesEmprestimo(parcelas, dataPrimeiraParcela);
        boolean servicoSolicitacaoEmprestimoEstaUP = verificarSeServicoSolicitacaoEmprestimoUP();


        if( !clientePodePedirEmprestimo || ! condicoesEmprestimoRegulares){

            solicitacaoEmprestimoDTO.setStatus(String.valueOf(SolicitacaoEmprestimoStatusDTO.NAO_AUTORIZADA));

            try {

                throw new AutorizacaoException(String.format("Solicitação de Empréstimo não autorizada: %s",solicitacaoEmprestimoDTO),logger);

            } catch (AutorizacaoException e) {
                e.printStackTrace();
            }finally{
                return solicitacaoEmprestimoDTO;
            }
        }


        if(!servicoSolicitacaoEmprestimoEstaUP){

            solicitacaoEmprestimoDTO.setStatus(String.valueOf(SolicitacaoEmprestimoStatusDTO.PROBLEMA_AO_SALVAR));

            try {
                throw new InfraStructrutureException("Serviço de solicitação de empréstimo neste momento está fora do ar",logger);
            } catch (InfraStructrutureException e) {
                e.printStackTrace();
            }finally{
                return solicitacaoEmprestimoDTO;
            }


        }



        if(clientePodePedirEmprestimo && condicoesEmprestimoRegulares && servicoSolicitacaoEmprestimoEstaUP){

                try {

                    solicitacaoEmprestimoDTO = this.solicitacaoEmprestimoFeignClient.criaSolicitacaoEmprestimo(solicitacaoEmprestimoDTO);

                }catch(Exception erro){
                    erro.printStackTrace();
                }finally{
                    return solicitacaoEmprestimoDTO;
                }
            }

        return solicitacaoEmprestimoDTO;

        }




    private boolean verificarSeServicoSolicitacaoEmprestimoUP() {

        List<InstanceInfo> listaInstancias = eurekaDiscoveryClient.getInstancesByVipAddressAndAppName(null,ParametrosConfig.SERVICO_SOLICITACAO_EMPRESTIMO.getValue(),false);

        listaInstancias.stream().forEach(instancia -> logger.info(instancia.toString()));
        boolean servicoUP = false;

        if(eurekaDiscoveryClient.getInstancesByVipAddressAndAppName(null,ParametrosConfig.SERVICO_SOLICITACAO_EMPRESTIMO.getValue(),false).get(0).getStatus().equals(InstanceInfo.InstanceStatus.UP))
            servicoUP =  true;

        return servicoUP;
    }


    public SolicitacaoEmprestimoDTO solicitarEmprestimoFallback(SolicitacaoEmprestimoDTO solicitacaoEmprestimoFallBack) {

        if(solicitacaoEmprestimoFallBack == null)
            return new SolicitacaoEmprestimoDTO();
        else{

            if(solicitacaoEmprestimoFallBack.getStatus().equals(SolicitacaoEmprestimoStatusDTO.PROBLEMA_AO_SALVAR))
                return reprocessarSolicitacaoEmprestimo(solicitacaoEmprestimoFallBack);

            if(solicitacaoEmprestimoFallBack.getStatus().equals(SolicitacaoEmprestimoStatusDTO.EM_AVALIACAO))
                return reprocessarSolicitacaoEmprestimo(solicitacaoEmprestimoFallBack);
        }

        return solicitacaoEmprestimoFallBack;

    }

    private SolicitacaoEmprestimoDTO reprocessarSolicitacaoEmprestimo(SolicitacaoEmprestimoDTO solicitacaoEmprestimoFallBack) {
                return this.solicitarEmprestimo(solicitacaoEmprestimoFallBack);
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
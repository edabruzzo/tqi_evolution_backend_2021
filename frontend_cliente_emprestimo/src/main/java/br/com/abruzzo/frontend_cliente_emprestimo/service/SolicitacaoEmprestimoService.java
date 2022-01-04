package br.com.abruzzo.frontend_cliente_emprestimo.service;

import br.com.abruzzo.dto.SolicitacaoClienteEmprestimoDTO;
import br.com.abruzzo.frontend_cliente_emprestimo.client.ISolicitacaoEmprestimoFeignClient;
import br.com.abruzzo.frontend_cliente_emprestimo.dto.SolicitacaoEmprestimoDTO;
import br.com.abruzzo.frontend_cliente_emprestimo.dto.SolicitacaoEmprestimoStatusDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Emmanuel Abruzzo
 * @date 04/01/2022
 */
@Service
public class SolicitacaoEmprestimoService {


    @Autowired
    ISolicitacaoEmprestimoFeignClient solicitacaoEmprestimoFeignClient;



    public SolicitacaoEmprestimoDTO solicitarNovoEmprestimo(SolicitacaoClienteEmprestimoDTO solicitacaoClienteEmprestimoDTO, Long idCliente) {

        SolicitacaoEmprestimoDTO solicitacaoEmprestimoDTO = new SolicitacaoEmprestimoDTO();
        solicitacaoEmprestimoDTO.setCpfCliente(solicitacaoClienteEmprestimoDTO.getCpf());
        solicitacaoEmprestimoDTO.setEmailCliente(solicitacaoClienteEmprestimoDTO.getEmail());
        solicitacaoEmprestimoDTO.setData_primeira_parcela(solicitacaoClienteEmprestimoDTO.getData_primeira_parcela());
        solicitacaoEmprestimoDTO.setStatus(String.valueOf(SolicitacaoEmprestimoStatusDTO.ABERTA));
        solicitacaoEmprestimoDTO.setNumeroMaximoParcelas(solicitacaoClienteEmprestimoDTO.getNumeroMaximoParcelas());
        solicitacaoEmprestimoDTO.setValor(solicitacaoClienteEmprestimoDTO.getValor());
        solicitacaoEmprestimoDTO.setIdCliente(idCliente);

        /**
         * Após a chamada para @link ISolicitacaoEmprestimoFeignClient se tudo correr bem já teremos
         * salvo uma solicitação de empréstimo em memória através do REDIS
         * no microsserviço responsável pelo gerenciamento de solicitações de empréstimo e,
         * caso o empréstimo tenha sido aprovado, já teremos um empréstimo salvo no microsserviço
         * específico de gerenciamento de empréstimos consolidados.
         *
         * Este método nos retornará um DTO solicitacaoEmprestimoSalvaDTO com a solicitação de empréstimo
         * salva no Redis, onde teremos o status da solicitação de empréstimo:
         *
         *     ABERTA,
         *     EM_PROCESSAMENTO,
         *     EM_AVALIACAO,
         *     NAO_AUTORIZADA,
         *     APROVADA,
         *     PROBLEMA_AO_SALVAR,
         *     CONSOLIDADA;
         *
         *
         * Se a solicitação de empréstimo tiver sido aprovada e salva no microsserviço de empréstimos,
         * já poderemos listá-la em tela.
         *
         */
        SolicitacaoEmprestimoDTO solicitacaoEmprestimoSalvaDTO = this.solicitacaoEmprestimoFeignClient.criaSolicitacaoEmprestimo(solicitacaoEmprestimoDTO);

        return solicitacaoEmprestimoSalvaDTO;

    }
}

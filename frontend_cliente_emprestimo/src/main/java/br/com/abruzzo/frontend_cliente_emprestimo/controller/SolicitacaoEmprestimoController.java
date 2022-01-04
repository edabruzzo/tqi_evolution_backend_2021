package br.com.abruzzo.frontend_cliente_emprestimo.controller;

import br.com.abruzzo.dto.SolicitacaoClienteEmprestimoDTO;
import br.com.abruzzo.frontend_cliente_emprestimo.client.IAutenticacaoUsuarioFeignClient;
import br.com.abruzzo.frontend_cliente_emprestimo.client.IClienteFeignClient;
import br.com.abruzzo.frontend_cliente_emprestimo.client.ISolicitacaoEmprestimoFeignClient;
import br.com.abruzzo.frontend_cliente_emprestimo.dto.ClienteDTO;
import br.com.abruzzo.frontend_cliente_emprestimo.dto.SolicitacaoEmprestimoDTO;
import br.com.abruzzo.frontend_cliente_emprestimo.dto.SolicitacaoEmprestimoStatusDTO;
import br.com.abruzzo.frontend_cliente_emprestimo.dto.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * Classe do tipo Controller que recebe os dados do cliente e da solicitação de empréstimo diretamente da tela
 * e envia os objetos separados ClienteDTO e SolicitacaoEmprestimoDTO para os FeignClients dos respectivos serviços
 * que fazem efetivamente as chamadas para os microsserviços responsáveis pelo gerenciamento de cliente e de solicitações
 * de empréstimo.
 *
 * @author Emmanuel Abruzzo
 * @date 02/01/2022
 */
@Controller
@RequestMapping("solicitacao-emprestimo")
public class SolicitacaoEmprestimoController {


    @Autowired
    IClienteFeignClient clienteFeignClient;

    @Autowired
    ISolicitacaoEmprestimoFeignClient solicitacaoEmprestimoFeignClient;

    @Autowired
    IAutenticacaoUsuarioFeignClient autenticacaoUsuarioFeignClient;




    @GetMapping("solicitar")
    public String solicitarEmprestimo(){
        return "emprestimo/solicitacao-emprestimo";

    }


    /**
     * Método para efetuar a solicitação de um novo empréstimo
     * Aqui partimos do princípio que somente um funcionário pode cadastrar uma nova solicitação
     * supondo que exista uma agência física para que o cliente realize tal pedido e tenha
     * seus documentos pessoais e comprovante de renda em mãos, para pedir um empréstimo.
     *
     *
     * @param solicitacaoClienteEmprestimoDTO
     * @return
     */
    @RolesAllowed({"FUNCIONARIO"})
    @PostMapping("novo")
    public String solicitarNovoEmprestimo(@RequestBody SolicitacaoClienteEmprestimoDTO solicitacaoClienteEmprestimoDTO){




        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setEmailAddress(solicitacaoClienteEmprestimoDTO.getEmail());
        usuarioDTO.setStatus("A");
        List<String> listaRoles = new ArrayList<>();
        listaRoles.add("CLIENTE");
        usuarioDTO.setRoles(listaRoles);
        usuarioDTO.setLoginAttempt(0);
        usuarioDTO.setPassword(solicitacaoClienteEmprestimoDTO.getSenha());
        usuarioDTO.setUsername(solicitacaoClienteEmprestimoDTO.getNome());


        /**
         * Após a chamada para @link IClienteFeignClient se tudo correr bem já teremos
         * salvo o usuário no microsserviço responsável pelo gerenciamento de autenticação de usuários
         * que nos retornará o clienteSalvoDTO já com um idUsuario preenchido
         */
        UsuarioDTO usuarioDTOSalvo = this.autenticacaoUsuarioFeignClient.criarUsuario(usuarioDTO);



        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setCpf(solicitacaoClienteEmprestimoDTO.getCpf());
        clienteDTO.setEmail(solicitacaoClienteEmprestimoDTO.getEmail());
        clienteDTO.setEnderecoCompleto(solicitacaoClienteEmprestimoDTO.getEnderecoCompleto());
        clienteDTO.setNome(solicitacaoClienteEmprestimoDTO.getNome());
        clienteDTO.setRenda(solicitacaoClienteEmprestimoDTO.getRenda());
        clienteDTO.setRg(solicitacaoClienteEmprestimoDTO.getRg());




        /**
         * Após a chamada para @link IClienteFeignClient se tudo correr bem já teremos
         * salvo o cliente no microsserviço responsável pelo gerenciamento de clientes
         * que nos retornará o clienteSalvoDTO já com um idCliente preenchido
         */
        ClienteDTO clienteSalvoDTO = this.clienteFeignClient.criaNovoCliente(clienteDTO);

        SolicitacaoEmprestimoDTO solicitacaoEmprestimoDTO = new SolicitacaoEmprestimoDTO();
        solicitacaoEmprestimoDTO.setCpfCliente(clienteDTO.getCpf());
        solicitacaoEmprestimoDTO.setEmailCliente(clienteDTO.getEmail());
        solicitacaoEmprestimoDTO.setData_primeira_parcela(solicitacaoClienteEmprestimoDTO.getData_primeira_parcela());
        solicitacaoEmprestimoDTO.setStatus(String.valueOf(SolicitacaoEmprestimoStatusDTO.ABERTA));
        solicitacaoEmprestimoDTO.setNumeroMaximoParcelas(solicitacaoClienteEmprestimoDTO.getNumeroMaximoParcelas());
        solicitacaoEmprestimoDTO.setValor(solicitacaoClienteEmprestimoDTO.getValor());
        solicitacaoEmprestimoDTO.setIdCliente(clienteSalvoDTO.getId());

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

        return "emprestimo";
    }



}

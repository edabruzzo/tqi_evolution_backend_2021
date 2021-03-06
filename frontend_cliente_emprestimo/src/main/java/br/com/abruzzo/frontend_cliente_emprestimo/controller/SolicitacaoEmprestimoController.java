package br.com.abruzzo.frontend_cliente_emprestimo.controller;

import br.com.abruzzo.dto.SolicitacaoClienteEmprestimoDTO;
import br.com.abruzzo.frontend_cliente_emprestimo.dto.ClienteDTO;
import br.com.abruzzo.frontend_cliente_emprestimo.dto.SolicitacaoEmprestimoDTO;
import br.com.abruzzo.frontend_cliente_emprestimo.dto.UsuarioDTO;
import br.com.abruzzo.frontend_cliente_emprestimo.service.AutenticacaoUsuarioService;
import br.com.abruzzo.frontend_cliente_emprestimo.service.ClienteService;
import br.com.abruzzo.frontend_cliente_emprestimo.service.SolicitacaoEmprestimoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    private static final Logger logger = LoggerFactory.getLogger(SolicitacaoEmprestimoController.class);


    @Autowired
    AutenticacaoUsuarioService autenticacaoUsuarioService;

    @Autowired
    SolicitacaoEmprestimoService solicitacaoEmprestimoService;

    @Autowired
    ClienteService clienteService;


    @GetMapping("solicitar")
    @RolesAllowed({"FUNCIONARIO", "SUPER_ADMIN"})
    public String solicitarEmprestimo(Model model) {

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
     * @return nomeView  emprestimo
     */
    @RolesAllowed({"FUNCIONARIO", "SUPER_ADMIN"})
    @PostMapping("novo")
    public String solicitarNovoEmprestimo(@ModelAttribute SolicitacaoClienteEmprestimoDTO solicitacaoClienteEmprestimoDTO, Model model){


        UsuarioDTO usuarioDTOSalvo = this.autenticacaoUsuarioService.criarUsuario(solicitacaoClienteEmprestimoDTO);

        /**
         * Após a chamada para @link IClienteFeignClient se tudo correr bem já teremos
         * salvo o cliente no microsserviço responsável pelo gerenciamento de clientes
         * que nos retornará o clienteSalvoDTO já com um idCliente preenchido
         */
        ClienteDTO clienteSalvoDTO = this.clienteService.criaNovoCliente(solicitacaoClienteEmprestimoDTO);

        SolicitacaoEmprestimoDTO solicitacaoEmprestimoSalvaDTO = this.solicitacaoEmprestimoService.solicitarNovoEmprestimo(solicitacaoClienteEmprestimoDTO, clienteSalvoDTO.getId());

        List<SolicitacaoEmprestimoDTO> listaSolicitacoesEmprestimoDTO = this.solicitacaoEmprestimoService.listarSolicitacoesEmprestimo();
        model.addAttribute("listaSolicitadoesEmprestimo",listaSolicitacoesEmprestimoDTO);

        return "emprestimo/solicitacao-emprestimo";
    }



}

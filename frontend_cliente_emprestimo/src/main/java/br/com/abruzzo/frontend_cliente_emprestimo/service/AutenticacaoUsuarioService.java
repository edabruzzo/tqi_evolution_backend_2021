package br.com.abruzzo.frontend_cliente_emprestimo.service;

import br.com.abruzzo.dto.SolicitacaoClienteEmprestimoDTO;
import br.com.abruzzo.frontend_cliente_emprestimo.exceptions.FuncionarioSemPrivilegioAdminTentandoCriarSUPERADMINException;
import br.com.abruzzo.frontend_cliente_emprestimo.feign_clients.IAutenticacaoUsuarioFeignClient;
import br.com.abruzzo.frontend_cliente_emprestimo.dto.UsuarioDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Emmanuel Abruzzo
 * @date 04/01/2022
 */

@Service
@RolesAllowed({"FUNCIONARIO","SUPER_ADMIN"})
public class AutenticacaoUsuarioService {

    private static final Logger logger = LoggerFactory.getLogger(AutenticacaoUsuarioService.class);

    @Autowired
    IAutenticacaoUsuarioFeignClient autenticacaoUsuarioFeignClient;



    public UsuarioDTO criarUsuario(SolicitacaoClienteEmprestimoDTO solicitacaoClienteEmprestimoDTO) {


        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setEmailAddress(solicitacaoClienteEmprestimoDTO.getEmail());
        usuarioDTO.setStatus("Ativo");

        List<String> listaRoles = new ArrayList<>();

        listaRoles.add("CLIENTE");

        usuarioDTO.setAuthorities(listaRoles);
        usuarioDTO.setLoginAttempt(0);
        usuarioDTO.setPassword(solicitacaoClienteEmprestimoDTO.getSenha());

        /**
         * Importante !
         *
         * Estamos usando o CPF como username do usuário logado
         * Isto permite algumas checagens de segurança no serviço de empréstimo
         * sem precisar bater no servidor de clientes.
         */
        usuarioDTO.setUsername(solicitacaoClienteEmprestimoDTO.getCpf());


        /**
         * Após a chamada para @link IClienteFeignClient se tudo correr bem já teremos
         * salvo o usuário no microsserviço responsável pelo gerenciamento de autenticação de usuários
         * que nos retornará o clienteSalvoDTO já com um idUsuario preenchido
         */
         UsuarioDTO usuarioDTOSalvo = this.autenticacaoUsuarioFeignClient.criarUsuario(usuarioDTO);

        return usuarioDTOSalvo;


    }


    public UsuarioDTO criarUsuario(UsuarioDTO usuarioDTO) {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean usuarioLogadoFuncionarioSemPrivilegioAdmin = ! authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("SUPER_ADMIN"));

        boolean usuarioNovoPossuiPrivilegioAdmin = usuarioDTO.getAuthorities().stream()
                .anyMatch(role -> role.equals("SUPER_ADMIN"));

        if(usuarioLogadoFuncionarioSemPrivilegioAdmin && usuarioNovoPossuiPrivilegioAdmin){

            String credenciaisUsuarioLogado = authentication.getCredentials().toString();

            String mensagemErro = "Tentativa de um Funcionário criar um SUPER_ADMIN no Sistema\n";
            mensagemErro += String.format("Usuário que fez a tentativa %s\n",credenciaisUsuarioLogado);
            mensagemErro += String.format("Usuário que ele tentou cadastrar: %s\n",usuarioDTO);
            throw new FuncionarioSemPrivilegioAdminTentandoCriarSUPERADMINException(mensagemErro, this.logger);



        }
        UsuarioDTO usuarioDTOSalvo = this.autenticacaoUsuarioFeignClient.criarUsuario(usuarioDTO);
        return usuarioDTOSalvo;

    }
}

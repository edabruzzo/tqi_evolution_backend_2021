package br.com.abruzzo.frontend_cliente_emprestimo.service;

import br.com.abruzzo.dto.SolicitacaoClienteEmprestimoDTO;
import br.com.abruzzo.frontend_cliente_emprestimo.client.IAutenticacaoUsuarioFeignClient;
import br.com.abruzzo.frontend_cliente_emprestimo.dto.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Emmanuel Abruzzo
 * @date 04/01/2022
 */
public class AutenticacaoUsuarioService {


    @Autowired
    IAutenticacaoUsuarioFeignClient autenticacaoUsuarioFeignClient;



    public UsuarioDTO criarUsuario(SolicitacaoClienteEmprestimoDTO solicitacaoClienteEmprestimoDTO) {


        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setEmailAddress(solicitacaoClienteEmprestimoDTO.getEmail());
        usuarioDTO.setStatus("Ativo");
        List<String> listaRoles = new ArrayList<>();

        listaRoles.add("CLIENTE");


        usuarioDTO.setRoles(listaRoles);
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
}

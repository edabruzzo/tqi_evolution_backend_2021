package br.com.abruzzo.frontend_cliente_emprestimo.feign_clients;

import br.com.abruzzo.frontend_cliente_emprestimo.dto.UsuarioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.security.RolesAllowed;

/**
 * @author Emmanuel Abruzzo
 * @date 03/01/2022
 */

@FeignClient("")
public interface IAutenticacaoUsuarioFeignClient {

    @RolesAllowed({"FUNCIONARIO","SUPER_ADMIN"})
    @RequestMapping("/usuario")
    public UsuarioDTO criarUsuario(@RequestBody UsuarioDTO usuarioDTO);
}

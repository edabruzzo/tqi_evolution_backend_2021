package br.com.abruzzo.frontend_cliente_emprestimo.feign_clients;

import br.com.abruzzo.frontend_cliente_emprestimo.dto.UsuarioDTO;
import feign.Request;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.security.RolesAllowed;

/**
 * @author Emmanuel Abruzzo
 * @date 03/01/2022
 */

@FeignClient("servico-autenticacao")
public interface IAutenticacaoUsuarioFeignClient {

    @RolesAllowed({"FUNCIONARIO","SUPER_ADMIN"})
    @RequestMapping(path="/usuario", method= RequestMethod.POST)
    public UsuarioDTO criarUsuario(@RequestBody UsuarioDTO usuarioDTO);
}

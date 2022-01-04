package br.com.abruzzo.controller;

import br.com.abruzzo.dto.UsuarioDTO;
import br.com.abruzzo.exceptions.UsuarioSemPrivilegioAdminTentandoSalvarAdminException;
import br.com.abruzzo.security.Usuario;
import br.com.abruzzo.service.UsuarioService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

/**
 * @author Emmanuel Abruzzo
 * @date 03/01/2022
 */

@RestController
@RequestMapping("/usuario")
public class UsuarioController {


    @Autowired
    private ModelMapper modelMapper;


    private UsuarioService usuarioService;


    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    /**
     *  Método responsável por cadastrar um usuário na base de dados do servidor de aplicação
     *  O acesso a este método é restrito a usuaŕios com privilégio de SUPER-ADMIN do sistema
     *  após request via chamada Rest utilizando o método HTTP POST
     *
     * @param usuarioDTO Objeto DTO JSON USUÁRIO que chega como payload no request body
     * @return    retorna um DTO com o usuario salvo no formato JSON
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @RolesAllowed({"FUNCIONARIO","SUPER_ADMIN"})
    public UsuarioDTO criarUsuario(@RequestBody UsuarioDTO usuarioDTO) {

        logger.info("Requisição para salvar um usuário na base do servidor de autenticação");
        logger.info("POST recebido no servidor de aplicação seguinte endpoint para criação do seguinte usuário:");
        logger.info(String.valueOf(usuarioDTO));

        Usuario usuarioSalvo = null ;


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean usuarioLogadoSemPrivilegioAdmin = authentication.getAuthorities().stream()
                .anyMatch(r -> ! r.getAuthority().equals("SUPER_ADMIN"));

        boolean estaTentandoSalvarAdmin = usuarioDTO.getRoles().stream().anyMatch(role -> role.equals("SUPER_ADMIN"));

        if(usuarioLogadoSemPrivilegioAdmin && estaTentandoSalvarAdmin){
            String credenciaisUsuarioLogado = authentication.getCredentials().toString();
            String mensagemErro = "Tentativa de um Funcionário cadastrar um Administrador no sistema\n";
            mensagemErro += String.format("Usuário que fez a tentativa %s",credenciaisUsuarioLogado);
            mensagemErro += "\nTentando salvar o seguinte usuário com perfil de Admin\n";
            mensagemErro += String.format("Usuário que fez a tentativa %s",usuarioDTO);
            throw new UsuarioSemPrivilegioAdminTentandoSalvarAdminException(mensagemErro, this.logger);
        }




        try{
            Usuario usuario = this.modelMapper.map(usuarioDTO, Usuario.class);
            usuarioSalvo = usuarioService.save(usuario);
            usuarioDTO = this.modelMapper.map(usuarioSalvo,UsuarioDTO.class);
            logger.info("Usuario %s foi salvo", usuario.toString());
            logger.info("%s", usuario);
            return usuarioDTO;

        }catch(Exception erro){
            logger.error(erro.getLocalizedMessage());
        }

        return usuarioDTO;
    }


}

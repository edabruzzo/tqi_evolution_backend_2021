package br.com.abruzzo.service;

import br.com.abruzzo.exceptions.UsuarioSemPrivilegioAdminTentandoSalvarAdminException;
import br.com.abruzzo.repository.UsuarioRepository;
import br.com.abruzzo.model.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author Emmanuel Abruzzo
 * @date 03/01/2022
 */
@Service
public class UsuarioService {


    private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);

    @Autowired
    private UsuarioRepository usuarioRepository;



    public Usuario salvarUsuarioBaseAutenticacao(Usuario usuario) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean usuarioLogadoSemPrivilegioAdmin = authentication.getAuthorities().stream()
                .anyMatch(r -> ! r.getAuthority().equals("SUPER_ADMIN"));

        boolean estaTentandoSalvarAdmin = usuario.getRoles().stream()
                .anyMatch(role -> role.equals("SUPER_ADMIN"));

        Usuario usuarioSalvo = null;

        if(usuarioLogadoSemPrivilegioAdmin && estaTentandoSalvarAdmin){

            String credenciaisUsuarioLogado = authentication.getCredentials().toString();

            String mensagemErro = "Tentativa de um Funcionário cadastrar um Administrador no sistema\n";
            mensagemErro += String.format("Usuário que fez a tentativa %s",credenciaisUsuarioLogado);
            mensagemErro += "\nTentando salvar o seguinte usuário com perfil de Admin\n";
            mensagemErro += String.format("Usuário a ser salvo: %s",usuario);

            throw new UsuarioSemPrivilegioAdminTentandoSalvarAdminException(mensagemErro, this.logger);
        }

        /**
         * Antes de salvar o usuário em banco é necessário encriptar a senha dele.
         * Estamos fazendo isso com o BCryptPasswordEncoder
         */
        String senhaEncriptadaUsuario = encriptarSenha(usuario.getPassword());
            usuario.setPassword(senhaEncriptadaUsuario);

        try{
            usuario = usuarioRepository.save(usuario);

            logger.info("Usuario %s foi salvo", usuario.toString());
            logger.info("%s", usuario);
            return usuario;

        }catch(Exception erro){
            logger.error(erro.getLocalizedMessage());
        }

        return usuarioSalvo;
    }

    private String encriptarSenha(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }


}
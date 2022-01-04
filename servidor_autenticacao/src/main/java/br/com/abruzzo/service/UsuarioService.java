package br.com.abruzzo.service;

import br.com.abruzzo.repository.UsuarioRepository;
import br.com.abruzzo.security.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Emmanuel Abruzzo
 * @date 03/01/2022
 */
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;


    public Usuario save(Usuario usuario) {
        Usuario usuarioSalvo = this.usuarioRepository.save(usuario);
        return usuarioSalvo;
    }
}

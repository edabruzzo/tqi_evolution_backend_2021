package br.com.abruzzo.repository;

import br.com.abruzzo.security.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Emmanuel Abruzzo
 * @date 03/01/2022
 */

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> { }

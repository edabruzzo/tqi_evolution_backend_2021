package br.com.abruzzo.tqi_backend_evolution_2021.repository;

import br.com.abruzzo.tqi_backend_evolution_2021.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Emmanuel Abruzzo
 * @date 06/01/2022
 */
public interface AutenticacaoUsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByUsername(String username);
}

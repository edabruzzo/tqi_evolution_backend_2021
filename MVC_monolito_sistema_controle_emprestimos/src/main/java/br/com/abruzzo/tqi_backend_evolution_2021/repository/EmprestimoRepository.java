package br.com.abruzzo.tqi_backend_evolution_2021.repository;

import br.com.abruzzo.tqi_backend_evolution_2021.model.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Emmanuel Abruzzo
 * @date 06/01/2022
 */
@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

    String consultaEmprestimosPorCpfCliente = "select e.* from tb_emprestimo e " +
            " inner join tb_cliente c on c.id = e.idCliente " +
            " where c.cpfCliente = :cpfCliente";

    @Query(value=consultaEmprestimosPorCpfCliente,nativeQuery = true)
    List<Emprestimo> findAllByCpf(@Param("cpfCliente") String cpfCliente);


    String consultaEmprestimosPorEmailCliente = "select e.* from tb_emprestimo e " +
            " inner join tb_cliente c on c.id = e.idCliente " +
            " where c.email = :emailCliente";

    @Query(value=consultaEmprestimosPorEmailCliente,nativeQuery = true)
    List<Emprestimo> findAllByEmail(@Param("emailCliente") String emailCliente);


}

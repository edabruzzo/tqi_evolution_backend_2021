/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.abruzzo.model;



import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * @author Emmanuel Abruzzo
 * @date 25/12/2021
 */

@Entity
@Table(name = "tb_emprestimo", catalog = "emprestimo", schema = "public")
@Data
@NoArgsConstructor
public class Emprestimo implements Serializable {


    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Column(name = "valor")
    private Double valor;

    @Column(name = "data_primeira_parcela")
    private Date data_primeira_parcela;

    @Column(name = "numeroMaximoParcelas")
    private Integer numeroMaximoParcelas;

    @Column(name="idCliente")
    private Integer idCliente;

    @Override
    public String toString() {
        return "Emprestimo{" +
                "id=" + id +
                ", valor=" + valor +
                ", data_primeira_parcela=" + data_primeira_parcela +
                ", numeroMaximoParcelas=" + numeroMaximoParcelas +
                ", idCliente=" + idCliente +
                '}';
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.abruzzo.model;

// https://stackoverflow.com/questions/31440496/hibernate-spatial-5-geometrytype
//import com.vividsolutions.jts.geom.Point;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author Emmanuel Abruzzo
 * @date 25/12/2021
 */

@Entity
@Table(name = "tb_emprestimo", catalog = "emprestimo", schema = "public")
public class Emprestimo implements Serializable {

    public Emprestimo() { }

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "uf")
    private Integer uf;

    @Column(name = "ibge")
    private Integer ibge;

    // 1st
    @Column(name = "lat_lon")
    private String geolocation;

    // 2nd
    @Column(name = "lat_lon", updatable = false, insertable = false)
    private Point location;


    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name="cod_tom")
    private int cod_tom;


}
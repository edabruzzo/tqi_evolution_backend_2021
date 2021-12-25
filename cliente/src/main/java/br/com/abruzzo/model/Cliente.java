package br.com.abruzzo.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;


@DynamoDBTable(tableName = "tb_heroes")
@Data
@NoArgsConstructor
public class Cliente {


    @DynamoDBHashKey
    @DynamoDBAutoGeneratedKey
    private String id;

    @DynamoDBAttribute
    private String nome;

    @DynamoDBAttribute
    private String email;

    @DynamoDBAttribute
    private String CPF;

    @DynamoDBAttribute
    private String RG;

    @DynamoDBAttribute
    private String endereçoCompleto;

    @DynamoDBAttribute
    private Double renda;

    @DynamoDBAttribute
    private String senha;


    public Cliente(String id, String nome, String email, String CPF, String RG, String endereçoCompleto, Double renda, String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.CPF = CPF;
        this.RG = RG;
        this.endereçoCompleto = endereçoCompleto;
        this.renda = renda;
        this.senha = senha;
    }



    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", CPF='" + CPF + '\'' +
                ", RG='" + RG + '\'' +
                ", endereçoCompleto='" + endereçoCompleto + '\'' +
                ", renda=" + renda +
                ", senha='" + senha + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(id, cliente.id) && Objects.equals(nome, cliente.nome) && Objects.equals(email, cliente.email) && Objects.equals(CPF, cliente.CPF) && Objects.equals(RG, cliente.RG) && Objects.equals(endereçoCompleto, cliente.endereçoCompleto) && Objects.equals(renda, cliente.renda) && Objects.equals(senha, cliente.senha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, email, CPF, RG, endereçoCompleto, renda, senha);
    }
}

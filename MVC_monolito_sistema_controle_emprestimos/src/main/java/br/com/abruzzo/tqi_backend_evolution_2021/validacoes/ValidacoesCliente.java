package br.com.abruzzo.tqi_backend_evolution_2021.validacoes;

import br.com.abruzzo.tqi_backend_evolution_2021.dto.EmprestimoDTO;

/**
 * @author Emmanuel Abruzzo
 * @date 06/01/2022
 */
public class ValidacoesCliente {


    /**
     *  Método responsável pela validação do CPF do cliente
     *
     * @param cpf
     * @return
     */
    public static boolean cpfValido(String cpf){

        /**
         * TODO implementar validação com regex
         */
        return true;
    };



    /**
     *  Método responsável pela validação do RG do cliente
     *
     * @param rg
     * @return
     */
    public static boolean rgValido(String rg){

        /**
         * TODO implementar validação com regex
         */
        return true;
    };


    /**
     * Método responsável pela validação da compatibilidade da renda do cliente com o valor do empréstimo solicitado
     *
     * @param emprestimoDTO
     * @return
     */
    public static boolean margemRendaCompativelComValorParcela(EmprestimoDTO emprestimoDTO){

        double margem = emprestimoDTO.getCliente().getRenda() * 0.3;
        double valorParcela = emprestimoDTO.getValor() / emprestimoDTO.getNumeroMaximoParcelas();

        return margem > valorParcela;
    }



}

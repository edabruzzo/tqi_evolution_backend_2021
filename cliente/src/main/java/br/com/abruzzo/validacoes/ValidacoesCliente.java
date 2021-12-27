package br.com.abruzzo.validacoes;

/**
 * @author Emmanuel Abruzzo
 * @date 26/12/2021
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
     * @param rendaCliente
     * @param valorEmprestimo
     * @param numeroParcelas
     * @return
     */
    public static boolean margemRendaCompativelComValorParcela(double rendaCliente, double valorEmprestimo, int numeroParcelas){

        /**
         * TODO implementar validação da renda
         */
        return true;
    };



}

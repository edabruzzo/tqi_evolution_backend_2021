package br.com.abruzzo.tqi_backend_evolution_2021.validacoes;

/**
 * @author Emmanuel Abruzzo
 * @date 06/01/2022
 */
public class ConsultaWebServicesExternos {

    public boolean consultarSCPC(String cpfCliente) {
        boolean situacaoRegular = true;

        /**
         * TODO Implementar consulta ao Webservice do SCPC
         */
        return situacaoRegular;
    }



    public boolean consultarSERASA(String cpfCliente) {

        boolean situacaoRegular = true;
        /**
         * TODO Implementar consulta ao Webservice do SERASA
         */
        return situacaoRegular;
    }


    public boolean consultarSistemaFinanceiroInterno(String cpfCliente, Double valorEmprestimoSolicitado) {

        boolean emprestimoAprovado = true;
        /**
         * TODO Implementar consulta ao Sistema Financeiro Interno de Avaliação de Crédito
         */
        return emprestimoAprovado;
    }
}

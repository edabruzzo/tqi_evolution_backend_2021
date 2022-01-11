package br.com.abruzzo.tqi_backend_evolution_2021.validacoes;

import java.sql.Date;
import java.util.Calendar;

/**
 * @author Emmanuel Abruzzo
 * @date 06/01/2022
 */
public class ValidacoesEmprestimo {


    /**
     *  Método responsável pela validação da data da primeira parcela
     *
     * @param dataPrimeiraParcela
     * @return
     */
    public static boolean validarDataPrimeiraParcela(Date dataPrimeiraParcela){

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 3);
        java.util.Date daquiATresMeses =  cal.getTime();
       return dataPrimeiraParcela.before(daquiATresMeses);

    }




    /**
     *  Método responsável pela validação do limite máximo de parcelas do empréstimo
     *
     * @param numeroParcelas
     * @return
     */
    public static boolean validarNumeroParcelas(int numeroParcelas){
        return numeroParcelas <= 60;
    };

}

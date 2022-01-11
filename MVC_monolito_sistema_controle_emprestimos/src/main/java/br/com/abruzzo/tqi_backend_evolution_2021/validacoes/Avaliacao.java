package br.com.abruzzo.tqi_backend_evolution_2021.validacoes;

import br.com.abruzzo.tqi_backend_evolution_2021.dto.EmprestimoDTO;
import br.com.abruzzo.tqi_backend_evolution_2021.exceptions.AutorizacaoException;
import br.com.abruzzo.tqi_backend_evolution_2021.exceptions.SituacaoIrregularCPFCliente;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Emmanuel Abruzzo
 * @date 06/01/2022
 */
public class Avaliacao {


   private Avaliacao() {
        throw new IllegalStateException("Utility class - não instanciar !!! ");
    }

    static ConsultaWebServicesExternos consultaWebServicesExternos;
    private static final Logger logger = LoggerFactory.getLogger(Avaliacao.class);


    public static boolean enviarParaProcessamento(EmprestimoDTO emprestimoDTO) {

        String cpfCliente = emprestimoDTO.getCliente().getCpf();
        boolean situacaoRegularSPC = consultaWebServicesExternos.consultarSCPC(cpfCliente);
        boolean situacaoRegularSERASA = consultaWebServicesExternos.consultarSERASA(cpfCliente);
        String mensagemErro = "";
        boolean emprestimoAprovado = false;
        boolean avaliacaoPositivaCreditoCliente = false;

        if(situacaoRegularSERASA && situacaoRegularSPC)
            avaliacaoPositivaCreditoCliente = consultaWebServicesExternos.consultarSistemaFinanceiroInterno(cpfCliente, emprestimoDTO.getValor());
        else{
            if (! situacaoRegularSPC){
                mensagemErro += String.format("Situação irregular no SCPC -> CPF {}",cpfCliente);
            }
            if (! situacaoRegularSERASA){
                mensagemErro += String.format("Situação irregular no SERASA -> CPF {}",cpfCliente);
            }

            try {
                throw new SituacaoIrregularCPFCliente(mensagemErro,logger);

            } catch (AutorizacaoException e) {
                e.printStackTrace();
                return emprestimoAprovado;

           }

        }

        if (avaliacaoPositivaCreditoCliente)
            emprestimoAprovado = true;

        return emprestimoAprovado;

    }

}

package br.com.abruzzo.avaliacao_emprestimos;

import br.com.abruzzo.exceptions.SituacaoIrregularCPFCliente;
import br.com.abruzzo.model.SolicitacaoEmprestimo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Emmanuel Abruzzo
 * @date 29/12/2021
 */
public class Avaliacao {


   private Avaliacao() {
        throw new IllegalStateException("Utility class - não instanciar !!! ");
    }

    static ConsultaWebServicesExternos consultaWebServicesExternos;
    private static final Logger logger = LoggerFactory.getLogger(Avaliacao.class);


    public static boolean enviarParaProcessamento(SolicitacaoEmprestimo solicitacaoEmprestimoSalva) {

        boolean situacaoRegularSPC = consultaWebServicesExternos.consultarSCPC(solicitacaoEmprestimoSalva.getCpfCliente());
        boolean situacaoRegularSERASA = consultaWebServicesExternos.consultarSERASA(solicitacaoEmprestimoSalva.getCpfCliente());
        String mensagemErro = "";
        boolean emprestimoAprovado = false;
        boolean avaliacaoPositivaCreditoCliente = false;

        if(situacaoRegularSERASA && situacaoRegularSPC)
            avaliacaoPositivaCreditoCliente = consultaWebServicesExternos.consultarSistemaFinanceiroInterno(solicitacaoEmprestimoSalva.getCpfCliente(), solicitacaoEmprestimoSalva.getValor());
        else{
            if (! situacaoRegularSPC){
                mensagemErro += String.format("Situação irregular no SCPC -> CPF {}",solicitacaoEmprestimoSalva.getCpfCliente());
            }
            if (! situacaoRegularSERASA){
                mensagemErro += String.format("Situação irregular no SERASA -> CPF {}",solicitacaoEmprestimoSalva.getCpfCliente());
            }

            try {
                throw new SituacaoIrregularCPFCliente(mensagemErro,logger);

            } catch (SituacaoIrregularCPFCliente e) {
                e.printStackTrace();
                return emprestimoAprovado;

           }

        }

        if (avaliacaoPositivaCreditoCliente)
            emprestimoAprovado = true;

        return emprestimoAprovado;

    }

}

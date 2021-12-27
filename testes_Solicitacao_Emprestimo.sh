#!/bin/bash

DATA_HORA=$(date +"%d_%m_%Y_%H_hs_%M_min")

diretorio_log_testes=/home/$USER/IdeaProjects/tqi_evolution_backend_2021/logs_testes

logSolicitacaoEmprestimosRequest=$diretorio_log_testes/TESTES_API_SOLICITACAO_EMPRESTIMO_$DATA_HORA.log

touch $logSolicitacaoEmprestimosRequest


function testaSolicitacaoEmprestimo {


echo 'Testando solicitação de empréstimo pelo cliente'

echo '\n'

curl --location --request GET 'http://localhost:8080/emprestimo/solicitar?idCliente=2&valor=50000&parcelas=60&dataPrimeiraParcela=2022-02-01'

echo '\n'

}

echo $(testaSolicitacaoEmprestimo) | tee $logSolicitacaoEmprestimosRequest >/dev/null

#testaAPIClientes
#testaAPIEmprestimos
call testaSolicitacaoEmprestimo >> $logSolicitacaoEmprestimosRequest

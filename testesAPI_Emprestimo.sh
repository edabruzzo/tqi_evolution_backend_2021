#!/bin/bash

DATA_HORA=$(date +"%d_%m_%Y_%H_hs_%M_min")

diretorio_log_testes=/home/$USER/IdeaProjects/tqi_evolution_backend_2021/logs_testes

logAPIEmprestimos=$diretorio_log_testes/TESTES_API_EMPRESTIMOS_$DATA_HORA.log

touch $logAPIEmprestimos


function testaAPIEmprestimos {


echo 'Criando empréstimo na base'

echo '\n'

curl --location --request POST 'http://localhost:8081/emprestimo' \
 --header 'Content-Type: application/json' \
 --data-raw '{
     "valor" : 10000,
     "data_primeira_parcela" : "2022-01-02",
     "numeroMaximoParcelas" : 60,
     "idCliente" : 1
 }'


echo '\n'
}



echo $(testaAPIEmprestimos) | tee $logAPIEmprestimos >/dev/null

call testaAPIEmprestimos >> $logAPIEmprestimos
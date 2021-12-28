#!/bin/bash


DATA_HORA=$(date +"%d_%m_%Y_%H_hs_%M_min")
DATA_DIA_MES=$(date +"%d_%m_%Y")
NOME_MICROSERVICO="Serviço de Gerenciamento de Empréstimo"
diretorio_log_testes=/home/$USER/IdeaProjects/tqi_evolution_backend_2021/logs_testes
diretorio_logs_testes_do_dia=$diretorio_log_testes/$DATA_DIA_MES
logRequests=$diretorio_logs_testes_do_dia/TESTES_$NOME_MICROSERVICO_$DATA_HORA.log

mkdir $diretorio_logs_testes_do_dia

touch $logRequests

function testarRequests {

echo "Iniciando testes da API do Microsserviço $NOME_MICROSERVICO: ${DATA_HORA}"


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

echo "Testes da API do microsserviço de $NOME_MICROSERVICO finalizados às $(date +"%d_%m_%Y_%H_hs_%M_min")"

}

call testarRequests >> $logRequests


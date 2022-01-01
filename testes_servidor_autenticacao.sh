#!/bin/bash

DATA_HORA=$(date +"%d_%m_%Y_%H_hs_%M_min")
DATA_DIA_MES=$(date +"%d_%m_%Y")
NOME_MICROSERVICO="Servidor de Autenticação"
diretorio_log_testes=/home/$USER/IdeaProjects/tqi_evolution_backend_2021/logs_testes
diretorio_logs_testes_do_dia=$diretorio_log_testes/$DATA_DIA_MES
logRequests=$diretorio_logs_testes_do_dia/TESTES_$NOME_MICROSERVICO_$DATA_HORA.log

mkdir $diretorio_logs_testes_do_dia

touch $logRequests


function testarRequests() {
echo '\n'
echo '\n'
echo '---------------------------------------------------------------------------------------------'
echo "Iniciando testes da API do Microsserviço $NOME_MICROSERVICO: ${DATA_HORA}"
echo '\n'
echo "Testando Autenticação do serviço cliente e de usuário de testes no Microsserviço: $NOME_MICROSERVICO"
echo '\n'

curl --location --request POST 'http://localhost:8088/oauth/token' \
--header 'Authorization: Basic Og==' \
--form 'scope="web"' \
--form 'grant_type="password"' \
--form 'username="joao"' \
--form 'password="joao_secret"'

echo '\n'

echo "Testes da API do microsserviço $NOME_MICROSERVICO finalizados às $(date +"%d_%m_%Y_%H_hs_%M_min")"
echo '\n'
echo '\n'
echo '---------------------------------------------------------------------------------------------'
echo '\n'
echo '\n'

}

#echo $(testarRequests) | tee $logRequests >/dev/null

#testaAPIClientes
#testaAPIEmprestimos
call testarRequests >> $logRequests

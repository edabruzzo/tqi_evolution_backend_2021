#!/bin/bash

DATA_HORA=$(date +"%d_%m_%Y_%H_hs_%M_min")
DATA_DIA_MES=$(date +"%d_%m_%Y")
NOME_MICROSERVICO="Serviço de gerenciamento de clientes"

nome_eureka_servico="servico-cliente"

diretorio_log_testes=/home/$USER/IdeaProjects/tqi_evolution_backend_2021/logs_testes
diretorio_logs_testes_do_dia=$diretorio_log_testes/$DATA_DIA_MES
logRequests=$diretorio_logs_testes_do_dia/TESTES_$NOME_MICROSERVICO_$DATA_HORA.log


inicio_request="http:/"

if [ -n "$1" ]; then
    inicio_request="http://$1"
fi


mkdir $diretorio_logs_testes_do_dia

touch $logRequests

function testarRequests() {
echo '\n'
echo '\n'
echo '---------------------------------------------------------------------------------------------'
echo "Iniciando testes da API do Microsserviço $NOME_MICROSERVICO: ${DATA_HORA}"
echo '\n'


echo 'Cadastrando cliente Andrea'

echo '\n'

curl --location --request POST "$inicio_request/$nome_eureka_servico/cliente" \
--header 'Content-Type: application/json' \
--data-raw '{
    "nome" : "Andrea",
    "email": "andrea@gmail.com",
    "cpf": "11111111111",
    "rg":  "11111111-1",
    "enderecoCompleto": "Rua 1",
    "renda":10000,
    "senha":"123"
 }'


echo '\n'


curl --location --request GET "$inicio_request/$nome_eureka_servico/cliente/1"


echo '\n'

echo 'Cadastrando cliente José'

echo '\n'

curl --location --request POST "$inicio_request/$nome_eureka_servico/cliente" \
--header 'Content-Type: application/json' \
--data-raw '{
    "nome" : "José",
    "email": "jose@gmail.com",
    "cpf": "222222222222",
    "rg":  "222222222-2",
    "enderecoCompleto": "Rua 2",
    "renda":8000,
    "senha":"456"
 }'

echo '\n'


curl --location --request GET "$inicio_request/$nome_eureka_servico/cliente"

 
echo '\n'

echo 'Atualizando Cliente de id 1'

echo '\n'

 curl --location --request PUT "$inicio_request/$nome_eureka_servico/cliente/1" \
--header 'Content-Type: application/json' \
--data-raw '{
    "nome" : "Andrea",
    "email": "andrea@gmail.com",
    "cpf":"9999999",
    "rg": "9999990-1",
    "renda":30000,
    "endereçoCompleto": "Rua 9",
    "senha":"123"
 }'


echo '\n'

curl --location --request GET "$inicio_request/$nome_eureka_servico/cliente/1"

echo '\n'

curl --location --request GET "$inicio_request/$nome_eureka_servico/cliente/"

echo '\n'

echo 'Deletando cliente de id 1'

echo '\n'

curl --location --request DELETE "$inicio_request/$nome_eureka_servico/cliente/1"

echo '\n'

curl --location --request GET "$inicio_request/$nome_eureka_servico/cliente/"

echo '\n'



echo '\n'

curl --location --request POST "$inicio_request/$nome_eureka_servico/cliente" \
--header 'Content-Type: application/json' \
--data-raw '{
    "nome" : "José",
    "email": "jose@gmail.com",
    "cpf": "222222222222",
    "rg":  "222222222-2",
    "enderecoCompleto": "Rua 2",
    "renda":8000,
    "senha":"456"
 }'

echo '\n'



echo "Testes da API do microsserviço $NOME_MICROSERVICO finalizados às $(date +"%d_%m_%Y_%H_hs_%M_min")"
echo '\n'
echo '\n'
echo '---------------------------------------------------------------------------------------------'
echo '\n'
echo '\n'

}


call testarRequests >> $logRequests



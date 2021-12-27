#!/bin/bash

DATA_HORA=$(date +"%d_%m_%Y_%H_hs_%M_min")

diretorio_log_testes=/home/$USER/IdeaProjects/tqi_evolution_backend_2021/logs_testes

logConfigServerRequests=$diretorio_log_testes/TESTES_CONFIG_SERVER_$DATA_HORA.log

touch $logConfigServerRequests


function testaConfigServerRequest {



echo 'Testando Configurações do Serviço de Clientes no Config Server'
echo '\n'
curl --location --request GET 'http://localhost:8888/servico_cliente/default'
echo '\n'



echo 'Testando Configurações do Serviço de Empréstimo no Config Server'
echo '\n'
curl --location --request GET 'http://localhost:8888/servico_emprestimo/default'
echo '\n'

}

echo $(testaConfigServerRequest) | tee logConfigServerRequests >/dev/null

#testaAPIClientes
#testaAPIEmprestimos
call testaConfigServerRequest >> $logConfigServerRequests

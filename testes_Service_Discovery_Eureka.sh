#!/bin/bash

DATA_HORA=$(date +"%d_%m_%Y_%H_hs_%M_min")

diretorio_log_testes=/home/$USER/IdeaProjects/tqi_evolution_backend_2021/logs_testes

logServiceDiscoveryEurekaRequest=$diretorio_log_testes/TESTES_SERVICE_DISCOVERY_EUREKA_SERVER_$DATA_HORA.log

touch $logServiceDiscoveryEurekaRequest


function testaServiceDiscoveryEurekaRequest {


echo 'Testando aplicações registradas no Eureka Server'

echo '\n'

curl --location --request GET 'http://localhost:8761/eureka/apps'

echo '\n'

}

echo $(testaServiceDiscoveryEurekaRequest) | tee $logServiceDiscoveryEurekaRequest >/dev/null

#testaAPIClientes
#testaAPIEmprestimos
call testaServiceDiscoveryEurekaRequest >> $logServiceDiscoveryEurekaRequest

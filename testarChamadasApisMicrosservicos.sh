#!/bin/bash

sh ./testes_config_server_servicos.sh
sh ./testes_Service_Discovery_Eureka.sh
sh ./testesAPICliente.sh
sh ./testesAPI_Emprestimo.sh
sh ./testes_Solicitacao_Emprestimo.sh
sh ./testes_zuul_proxy_gateway.sh

sh ./testes_servidor_autenticacao.sh

zuul_proxy_gateway="localhost:5555"

# -- FAZENDO OS TESTES UTILIZANDO O GATEWAY PARA FAZER OS REQUESTS
sh ./testesAPICliente.sh  zuul_proxy_gateway
sh ./testesAPI_Emprestimo.sh zuul_proxy_gateway
sh ./testes_Solicitacao_Emprestimo.sh zuul_proxy_gateway

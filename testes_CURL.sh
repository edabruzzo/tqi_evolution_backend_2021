#!/bin/bash

echo 'Cadastrando cliente Andrea'

echo '\n'

curl --location --request POST 'http://localhost:8080/cliente' \
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


curl --location --request GET 'http://localhost:8080/cliente/1'


echo '\n'

echo 'Cadastrando cliente José'

echo '\n'
curl --location --request POST 'http://localhost:8080/cliente' \
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


 curl --location --request GET 'http://localhost:8080/cliente'

 
echo '\n'
 echo 'Atualizando Cliente de id 1'

echo '\n'
 curl --location --request PUT 'http://localhost:8080/cliente/1' \
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

curl --location --request GET 'http://localhost:8080/cliente/1'

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

curl --location --request GET 'http://localhost:8080/cliente/'

echo '\n'

echo 'Deletando cliente de id 1'

echo '\n'
curl --location --request DELETE 'http://localhost:8080/cliente/1'

echo '\n'

curl --location --request GET 'http://localhost:8080/cliente/'

echo '\n'

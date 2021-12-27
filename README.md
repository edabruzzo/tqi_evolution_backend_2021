![tqi_evolution_backend_2021](tqi_banner.jpg)

# Projeto Final TQI backend em parceria com a Digital Innovation One
## Projeto entregue como requisito final para avançar no processo seletivo da TQI - JAVA DEVELOPER

## Emmanuel de Oliveira Abruzzo - Dezembro/2021 - Janeiro/2022
+ Projeto Final TQI - DIGITAL INNOVATION ONE - BOOTCAMP - TQI-JAVA-DEVELOPER - Dez/2021



### Arquitetura escolhida
  Optamos por modelar nossa aplicação em microserviços para garantir independência de cada funcionalidade do resto da aplicação induz ao baixo acoplamento. Com isso, temos mais facilidade de lidar com questões técnicas, que são fortemente influenciadas pelos requisitos funcionais e não funcionais do negócio que estamos informatizando.
  
  Pretendemos, com isso baixo acoplamento entre os módulos, que solicitam serviços através de chamadas Rest, sendo que cada serviço expõe recursos 
  
  Para controle de acesso aos recursos e segurança da aplicação optamos pela implementação de um módulo de segurança para garantir acesso a recursos por:
  * autenticação 
  * papeis 


### Conceitos trabalhados 

Um microsserviço é a implementação de um contexto menor do domínio da aplicação, 

Teremos os seguintes microsserviços: Cliente, Empréstimo, Service Discovery, Segurança e Frontend

Quebraremos o domínio em contextos menores (bounded context)
  
Optamos inicialmente pelo uso de Webflux para que nossos serviços, que expõem recursos, fossem reativos e elásticos

Optamos inicialmente pelo uso do DynamoDB da AWS como banco de dados NoSQL (não-relacional), para garantir boa escalabilidade

Tivemos alguns problemas com o DynamoDB e, por fim, optamos pelo uso de bancos relacionais

Criamos branches específicas para uso de banco não relacional e também o conceito de API reativa  

Diante do deadline para entrega do projeto optamos por trabalhar na branch master com banco relacional (PostgreSQL) e API não reativa
  
Porém, deixamos a branch 'reativa com Dynamo' preparada para evolução e posterior troca de paradigma após entrega do MVP (Minimum Viable Product)

* Vide notas abaixo em relação ao DynamoDB


### Opção por uma branch específica para testar banco não-relacional  DynamoDB e outra branch para Postgres (relacional)
  
Durante o processo de desenvolvimento houve problemas na execução de operações de banco no DynamoDB local pela aplicação 

Optamos por separar o projeto do Microserviço de clientes em duas branchs específicas para cada banco

Trabalhar com banco de dados não relacional (ainda mais banco de alta perfomance como o DynamoDB da AWS é ótimo. Mas percebemos claramente o quanto a escolha do tipo de banco (relacional / não-relacional) tem impacto na estrutura do projeto

### Benefícios de bancos NoSQL (não-relacionais) em aplicações de microserviços
    
Estamos trabalhando inicialmente com banco relacional PostgreSQL para ter um produto minimamente viável

A ideia, porém, é trabalhar com ElasticSearch ou DynamoDB como bancos não relacionais para melhorar a performance e preparar a aplicação para escalar de forma fácil e performática

Percebemos claremente que bancos relacionais têm a tendência de criar alto acoplamento entre serviços
    
Optamos por utilizar um DTO no serviço Cliente para fazer uma espécie de Mock da entidade empréstimo e mandá-lo na requisição

Com isso, não precisamos criar dependência entre serviços com mapeamento objeto relacional e chaves estrangeiras

Basta que o Cliente saiba como invocar o serviço Rest exposto pelo serviço Empréstimo ao solicitar um empréstimo

Em nenhum momento é criada uma dependência relacional entre as Entidades JPA Cliente e Emprestimo
    


#### AWS CLI
+ https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/getting-started-step-1.html
+ https://github.com/aws/aws-sdk-java

#### DynamoDB AWS
+ https://www.tutorialspoint.com/dynamodb/dynamodb_load_table.htm
```shell
  aws dynamodb create-table --table-name tb_cliente --attribute-definitions  AttributeName=id,AttributeType=S  --key-schema AttributeName=id,KeyType=HASH   --provisioned-throughput  ReadCapacityUnits=10,WriteCapacityUnits=5  --table-class STANDARD

```  

+ https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/GettingStarted.Java.01.html


### Service Discovery

Quando trabalhamos com arquiteturas orientadas a eventos que possuam microsserviços distribuídos entre diferentes hosts,
precisamos de uma estratégia para mapeamento dos endereços onde os serviços estão hospedados, porta para acesso 
e um mecanismo para traduzir a URL do serviço em um nome mapeado

Desta forma os demais serviços não precisam conhecer o endereço exato dos serviços dos quais dependem, bastando para tanto
se registrarem em um servidor que fará este mapeamento e será responsável por traduzir as diversas URLs para nomes mapeados.

* #### Service registry 
Servidor central, onde todos os microsserviços ficam cadastrados (nome e IP/porta)
A resolução do IP/porta através do nome do microsserviço nas requisições

* #### Service discovery 
Mecanismo de descoberta do IP do microsserviço pelo nome
Dessa forma, nenhum microsserviço fica acoplado ao outro pelo IP/porta
A implementação do service registry através do Eureka Server


Para tanto, utilizamos o Eureka Server e dizemos a cada microsserviço que ele deve se registrar no Eureka Server.

  * #### Integração entre microsserviços com RestTemplate 
  O RestTemplate do Spring permite chamadas HTTP de alto nível



### Config Server

Os microsserviços são preparados para ambiente Cloud, cuja precificação é diretamente proporcional à quantidade 
de máquinas e ao uso de seus recursos de infraestrutura. 

A fim de reduzir esse custo, aplicações de microsserviços podem ser escaladas automaticamente
de acordo com a demanda e, em questão de segundos, são disponibilizadas funcionalidades 
do que antes estava dentro de um servidor de aplicação numa única aplicação clássica monolítica. 

Nesse cenário, configurar manualmente os servidores com as configurações necessárias para cada aplicação 
é impraticável, daí a mecessidade de um Config Server








#### Chamadas REST para o Eureka Server
```shell

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

echo $(testaSolicitacaoEmprestimo) | tee $logSolicitacaoEmprestimosRequest >/dev/null

#testaAPIClientes
#testaAPIEmprestimos
call testaServiceDiscoveryEurekaRequest >> $logServiceDiscoveryEurekaRequest





```





#### Microserviços integrados num sistema de cadastro de clientes e pedido de empréstimos

#### GITHUB:
+ https://github.com/edabruzzo/tqi_evolution_backend_2021
#### LINKEDIN:
+ https://www.linkedin.com/in/emmanuel-abruzzo-8ba80a36/
#### DISCORD - DIO
+ Discord: https://discord.gg/AWxMaerJ
#### DIGITAL INNOVATION ONE
+ https://digitalinnovation.one/
+ #### Video deploy
+ https://youtu.be/6Vd3WYr5r3E
+ https://youtu.be/MM0CQyWEQ7s


## Endpoints das APIs expostos pelos microsserviços - testes executados com POSTMAN
  Os testes da API foram realizados utilizando o Postman e abaixo estão as chamadas via CURL para testes

  Criamos arquivos de testes bash scripts com chamadas CURL para as APIs

### Microservice Cliente
  

```shell
#!/bin/bash

DATA_HORA=$(date +"%d_%m_%Y_%H_hs_%M_min")

diretorio_log_testes=/home/$USER/IdeaProjects/tqi_evolution_backend_2021/logs_testes

logAPIClientes=$diretorio_log_testes/TESTES_API_CLIENTES_$DATA_HORA.log

touch $logAPIClientes


function testaAPIClientes {

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

curl --location --request GET 'http://localhost:8080/cliente/'

echo '\n'

echo 'Deletando cliente de id 1'

echo '\n'

curl --location --request DELETE 'http://localhost:8080/cliente/1'

echo '\n'

curl --location --request GET 'http://localhost:8080/cliente/'

echo '\n'
}

echo $(testaAPIClientes) | tee $logAPIClientes >/dev/null

call testaAPIClientes >> $logAPIClientes
```


### Microservice Empréstimo

```shell
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

echo 'PUT - atualização de empréstimo existente'

echo '\n'

curl --location --request PUT 'http://localhost:8081/emprestimo/1' \
--header 'Content-Type: application/json' \
--data-raw '{
    "valor" : 1555555555,
    "data_primeira_parcela" : "2030-10-30",
    "numeroMaximoParcelas" : 60,
    "idCliente" : 1
}'

echo '\n'

echo 'GET - listar empréstimo por id'
echo '\n'
curl --location --request GET 'http://localhost:8081/emprestimo/1'
echo '\n'



echo 'GET - listar empréstimos'
echo '\n'
curl --location --request GET 'http://localhost:8081/emprestimo/'
echo '\n'

echo 'GET - listar empréstimos'
echo '\n'
curl --location --request GET 'http://localhost:8081/emprestimo/'
echo '\n'


}



echo $(testaAPIEmprestimos) | tee $logAPIEmprestimos >/dev/null

call testaAPIEmprestimos >> $logAPIEmprestimos
```




#### EXPLORAÇÃO DE CONCEITOS DE ORIENTAÇÃO A OBJETO

    O projeto explora também herança na criação de Business Exceptions e outras unchecked Exceptions


## Requirements

* Linux
  Meu sistema já é Ubuntu 18.04 (release "Bionic")
* Git
* Java 8
* Docker

* IntelliJ Community ou NetBeans
    * Desenvolvido no IntelliJ Idea
    * Adorei o IntelliJ, pois já estava muito familiarizado com o PyCharm da JetBrains
    * Sempre desenvolvi em NetBeans, mas depois deste BootCamp, já migrei para o IntelliJ

* Heroku CLI

## DataBase

### Postgres

* [Postgres Docker Hub](https://hub.docker.com/_/postgres)

```
docker run --name tqi_evolution_backend_2021 -d -p 5432:5432 -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=super_password -e POSTGRES_DB=tqi_evolution_backend_2021 postgres

```

### Populate

* [data](https://github.com/chinnonsantos/sql-paises-estados-cidades/tree/master/PostgreSQL)


###### Atenção ! Se você possui o Postgresql instalado na maquina local das duas uma:

* Terá que parar o serviço do postgres que roda na porta 5432 antes de rodar a imagem docker

```
/etc/init.d/postgres stop
```

* Mudar a porta exposta pela imagem para não mapear a 5432 que já estará em uso no seu banco local

#### Escrevi um artigo na DIO a respeito do conflito de portas:

+ https://digitalinnovation.one/artigos/avoid-port-conflicts-when-configuring-postgresql-image-from-docker-on-your-local-machine-with-another-postgresql-instance


```
/etc/init.d/postgresql stop && sudo docker run -it --rm --net=host -v $PWD:/tmp postgres /bin/bash

cd tmp/ && for file in *.sql; do psql -U postgres -h localhost -p 5432 -d cities -f $file; done

psql -h localhost -U postgres -p 5432 -d tqi_evolution_backend_2021

##### Transportadas para um arquivo changelog de migração *.sql para serem executadas no loop for acima

### Access

```
docker exec -it tqi_evolution_backend_2021 /bin/bash

psql -U postgres tqi_evolution_backend_2021
```


## Spring Boot

* [https://start.spring.io/](https://start.spring.io/)

+ Java 8
+ MAVEN Project
    + Devido à minha familiaridade, optei por desenvolver e construir o projeto e gerenciar dependências com Maven
+ Jar
+ Spring Web
+ Spring Data JPA
+ Spring Cloud
# Eureka Server
+ PostgreSQL Driver
  Versão: 42.2.24
### Spring Data

* [jpa.query-methods](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods)

### Properties

* [appendix-application-properties](https://docs.spring.io/spring-boot/docs/current/reference/html/appendix-application-properties.html)
* [jdbc-database-connectio](https://www.codejava.net/java-se/jdbc/jdbc-database-connection-url-for-common-databases)

### Types

* [JsonTypes](https://github.com/vladmihalcea/hibernate-types)
* [UserType](https://docs.jboss.org/hibernate/orm/3.5/api/org/hibernate/usertype/UserType.html)

## Heroku

* [DevCenter](https://devcenter.heroku.com/articles/getting-started-with-gradle-on-heroku)

## Code Quality

### PMD

+ https://pmd.github.io/pmd-6.8.0/index.html

### Checkstyle

+ https://checkstyle.org/

+ https://checkstyle.org/google_style.html

+ http://google.github.io/styleguide/javaguide.html

```
wget https://raw.githubusercontent.com/checkstyle/checkstyle/master/src/main/resources/google_checks.xml
```


### OUTRAS REFERÊNCIAS ÚTEIS

#### SPRING REST APPLICATION
+  https://www.oracle.com/br/technical-resources/articles/dsl/crud-rest-sb2-hibernate.html
+  https://spring.io/guides/gs/rest-service/
+  https://spring.io/guides
+  https://www.tutorialspoint.com/spring/calling_stored_procedure.htm
+  https://www.tutorialspoint.com/spring_boot/spring_boot_consuming_restful_web_services.htm


#### GIT PULL-REQUEST TUTORIAL
+ https://www.digitalocean.com/community/tutorials/how-to-create-a-pull-request-on-github

#### Discussão sobre injeção de dependência
+ https://www.oracle.com/br/technical-resources/articles/dsl/crud-rest-sb2-hibernate.html

+ "O Spring automaticamente fornece a injeção de dependência. Este exemplo não está usando a anotação @Autowired pois não é mais considerado
  uma boa prática para injeção de dependência de atributos obrigatórios. Desde a versão 4 do
  Spring a prática recomendada é o uso de injeção de dependência por construtor
  (as IDEs mais modernas inclusive apresentam um alerta quando fazemos o uso do @Autowired)."


#### SHELL JAVA
+ https://stackabuse.com/executing-shell-commands-with-java/


#### STRING FORMAT
+ https://www.javatpoint.com/java-string-format


#### TESTE SPRING
+ https://spring.io/guides/gs/testing-web/
+ https://www.baeldung.com/spring-boot-testing
+ https://www.bezkoder.com/spring-boot-unit-test-jpa-repo-datajpatest/
+ https://www.tutorialspoint.com/spring_boot/spring_boot_rest_controller_unit_test.htm
+ https://www.baeldung.com/spring-mock-rest-template


#### GEOMAPPING - POSTGRESQL X HIBERNATE
+ https://stackoverflow.com/questions/27624940/map-a-postgis-geometry-point-field-with-hibernate-on-spring-boot
+ https://docs.jboss.org/hibernate/orm/5.4/userguide/html_single/Hibernate_User_Guide.html#spatial
+ https://www.baeldung.com/hibernate-spatial
+ https://stackoverflow.com/questions/31440496/hibernate-spatial-5-geometrytype
+ https://github.com/Wisienkas/springJpaGeo
+ https://www.postgresql.org/docs/12/functions-geometry.html

#### USANDO NamedQueries ou NamedNativeQueries COM SPRING DATA
+ https://thorben-janssen.com/spring-data-jpa-named-queries/
+ https://howtodoinjava.com/jpa/jpa-native-query-example-select/
+ https://zetcode.com/springboot/datajpanamedquery/

#### ERROR HANDLING SPRING
+ https://www.baeldung.com/exception-handling-for-rest-with-spring

#### Redirection controllers
+ https://www.baeldung.com/spring-redirect-and-forward


#### INTERCEPTORS SPRING X CDI
+ https://rhuanrocha.net/2019/01/06/creating-logger-with-aop-using-cdi-interceptor/
+ https://www.tutorialspoint.com/spring_boot/spring_boot_interceptor.htm
+ https://stackoverflow.com/questions/31082981/spring-boot-adding-http-request-interceptors

#### Conversor String para Enum para Receber parametro Minusculo
+ https://www.baeldung.com/spring-enum-request-param
+ https://www.baeldung.com/spring-type-conversions

#### Deploy da aplicação Springboot Maven no Heroku
    Usando o plugin Heroku do Maven

```
$ mvn clean dependency:copy-dependencies package heroku:deploy

[INFO] -----> Creating build...
[INFO]        - file: /tmp/heroku-deploy14168471124361201653source-blob.tgz
[INFO]        - size: 131MB
[INFO] -----> Uploading build...
^[[5F[INFO]        - success
[INFO] -----> Deploying...
[INFO] remote: 
[INFO] remote: -----> Building on the Heroku-20 stack
[INFO] remote: -----> Using buildpack: heroku/jvm
[INFO] remote: -----> heroku-maven-plugin app detected
[INFO] remote: -----> Installing JDK 1.8... done
[INFO] remote: -----> Discovering process types
[INFO] remote:        Procfile declares types -> web
[INFO] remote: 
[INFO] remote: -----> Compressing...
[INFO] remote:        Done: 183.6M
[INFO] remote: -----> Launching...
[INFO] remote:        Released v30
[INFO] remote:        https://fierce-atoll-34490.herokuapp.com/ deployed to Heroku
[INFO] remote: 
[INFO] -----> Done
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  04:41 min
[INFO] Finished at: 2021-11-17T09:53:40-03:00
[INFO] ------------------------------------------------------------------------
```
```
$ heroku logs --tail


2021-11-17T12:48:12.078750+00:00 heroku[run.7699]: State changed from up to complete
2021-11-17T12:53:22.000000+00:00 app[api]: Build started by user emmanuel.oliveira3@gmail.com
2021-11-17T12:53:38.991791+00:00 app[api]: Release v30 created by user emmanuel.oliveira3@gmail.com
2021-11-17T12:53:38.991791+00:00 app[api]: Deploy 0.0.1-SNAPSHOT by user emmanuel.oliveira3@gmail.com
2021-11-17T12:53:39.000000+00:00 app[api]: Build succeeded
2021-11-17T12:53:40.044875+00:00 heroku[web.1]: State changed from crashed to starting
2021-11-17T12:53:45.766843+00:00 heroku[web.1]: Starting process with command `java -Dserver.port=22517 -jar -Dspring.profiles.active=heroku target/project_cities_api-0.0.1-SNAPSHOT.jar`
2021-11-17T12:53:47.956815+00:00 app[web.1]: Setting JAVA_TOOL_OPTIONS defaults based on dyno size. Custom settings will override them.
2021-11-17T12:53:47.964565+00:00 app[web.1]: Picked up JAVA_TOOL_OPTIONS: -Xmx300m -Xss512k -XX:CICompilerCount=2 -Dfile.encoding=UTF-8
2021-11-17T12:53:49.282528+00:00 app[web.1]: 
2021-11-17T12:53:49.282650+00:00 app[web.1]: .   ____          _            __ _ _
2021-11-17T12:53:49.282692+00:00 app[web.1]: /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
2021-11-17T12:53:49.282733+00:00 app[web.1]: ( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
2021-11-17T12:53:49.282777+00:00 app[web.1]: \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
2021-11-17T12:53:49.282815+00:00 app[web.1]: '  |____| .__|_| |_|_| |_\__, | / / / /
2021-11-17T12:53:49.282852+00:00 app[web.1]: =========|_|==============|___/=/_/_/_/
2021-11-17T12:53:49.283984+00:00 app[web.1]: :: Spring Boot ::                (v2.5.6)
2021-11-17T12:53:49.284012+00:00 app[web.1]: 
2021-11-17T12:53:49.439824+00:00 app[web.1]: 2021-11-17 12:53:49.437  INFO 4 --- [           main] b.c.d.a.p.ProjectCitiesApiApplication    : Starting ProjectCitiesApiApplication v0.0.1-SNAPSHOT using Java 1.8.0_312-heroku on 6618f368-20b2-4544-85a9-782d23670d34 with PID 4 (/app/target/project_cities_api-0.0.1-SNAPSHOT.jar started by u9248 in /app)
2021-11-17T12:53:49.440541+00:00 app[web.1]: 2021-11-17 12:53:49.440  INFO 4 --- [           main] b.c.d.a.p.ProjectCitiesApiApplication    : The following profiles are active: heroku
2021-11-17T12:53:50.379161+00:00 app[web.1]: 2021-11-17 12:53:50.378  INFO 4 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Bootstrapping Spring Data JPA repositories in DEFAULT mode.
2021-11-17T12:53:50.468044+00:00 app[web.1]: 2021-11-17 12:53:50.467  INFO 4 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Finished Spring Data repository scanning in 63 ms. Found 3 JPA repository interfaces.
2021-11-17T12:53:51.169628+00:00 app[web.1]: 2021-11-17 12:53:51.169  INFO 4 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 22517 (http)
2021-11-17T12:53:51.181191+00:00 app[web.1]: 2021-11-17 12:53:51.181  INFO 4 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2021-11-17T12:53:51.181338+00:00 app[web.1]: 2021-11-17 12:53:51.181  INFO 4 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.54]
2021-11-17T12:53:51.235918+00:00 app[web.1]: 2021-11-17 12:53:51.235  INFO 4 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2021-11-17T12:53:51.236045+00:00 app[web.1]: 2021-11-17 12:53:51.235  INFO 4 --- [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 1713 ms
2021-11-17T12:53:51.480657+00:00 app[web.1]: 2021-11-17 12:53:51.479  INFO 4 --- [           main] o.hibernate.jpa.internal.util.LogHelper  : HHH000204: Processing PersistenceUnitInfo [name: default]
2021-11-17T12:53:51.539039+00:00 app[web.1]: 2021-11-17 12:53:51.538  INFO 4 --- [           main] org.hibernate.Version                    : HHH000412: Hibernate ORM core version 5.4.32.Final
2021-11-17T12:53:51.851441+00:00 app[web.1]: 2021-11-17 12:53:51.851  INFO 4 --- [           main] o.hibernate.annotations.common.Version   : HCANN000001: Hibernate Commons Annotations {5.1.2.Final}
2021-11-17T12:53:51.957733+00:00 app[web.1]: 2021-11-17 12:53:51.957  INFO 4 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
2021-11-17T12:53:52.297792+00:00 app[web.1]: 2021-11-17 12:53:52.297  INFO 4 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
2021-11-17T12:53:52.340627+00:00 app[web.1]: 2021-11-17 12:53:52.340  INFO 4 --- [           main] org.hibernate.dialect.Dialect            : HHH000400: Using dialect: org.hibernate.dialect.PostgreSQL10Dialect
2021-11-17T12:53:53.089442+00:00 app[web.1]: 2021-11-17 12:53:53.089  INFO 4 --- [           main] o.h.e.t.j.p.i.JtaPlatformInitiator       : HHH000490: Using JtaPlatform implementation: [org.hibernate.engine.transaction.jta.platform.internal.NoJtaPlatform]
2021-11-17T12:53:53.321481+00:00 app[web.1]: 2021-11-17 12:53:53.321  INFO 4 --- [           main] j.LocalContainerEntityManagerFactoryBean : Initialized JPA EntityManagerFactory for persistence unit 'default'
2021-11-17T12:53:54.136065+00:00 app[web.1]: 2021-11-17 12:53:54.135  WARN 4 --- [           main] JpaBaseConfiguration$JpaWebConfiguration : spring.jpa.open-in-view is enabled by default. Therefore, database queries may be performed during view rendering. Explicitly configure spring.jpa.open-in-view to disable this warning
2021-11-17T12:53:54.789421+00:00 app[web.1]: 2021-11-17 12:53:54.789  INFO 4 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 22517 (http) with context path ''
2021-11-17T12:53:54.801433+00:00 app[web.1]: 2021-11-17 12:53:54.801  INFO 4 --- [           main] b.c.d.a.p.ProjectCitiesApiApplication    : Started ProjectCitiesApiApplication in 6.057 seconds (JVM running for 6.837)
2021-11-17T12:53:55.095750+00:00 heroku[web.1]: State changed from starting to up

```

+ https://devcenter.heroku.com/articles/deploying-spring-boot-apps-to-heroku
+ https://stackoverflow.com/questions/32490217/java-web-app-on-heroku-unable-to-access-jarfile
+ http://maven.apache.org/guides/getting-started/maven-in-five-minutes.html
+ https://devcenter.heroku.com/articles/deploying-spring-boot-apps-to-heroku


#### Heroku DB Migrations on Release Phase
+ https://devcenter.heroku.com/articles/running-database-migrations-for-java-apps
+ https://devcenter.heroku.com/articles/release-phase

#### Conexão Banco de Dados PostgreSQL Heroku Add-on
+ Heroku configura no ambiente uma variável com URL para conexão ao banco de dados criado na AWS:

```
heroku config

=== fierce-atoll-34490 Config Vars
DATABASE_URL: postgres://qmghvbmblxrlba:f623f1e760b51ab39cfc64a8549e62a1f814346dc203d252e0b5699343b298a8@ec2-3-227-149-67.compute-1.amazonaws.com:5432/d7q52pdubr9p5l
```

Você pode se conectar ao banco via psql utilizando estas credenciais
e a senha que pode ser vista no seu dashboard no Heroku Dev Center

```
heroku pg:psql postgresql-clean-54438 --app fierce-atoll-34490
```
Outro meio de se conectar diretamente com psql com as credenciais de banco e host fornecidas pelo próprio Heroku

```
$ heroku pg:info

=== DATABASE_URL
Plan:                  Hobby-dev
Status:                Available
Connections:           0/20
PG Version:            13.4
Created:               2021-11-17 01:49 UTC
Data Size:             9.2 MB/1.00 GB (In compliance)
Tables:                3
Rows:                  5849/10000 (In compliance)
Fork/Follow:           Unsupported
Rollback:              Unsupported
Continuous Protection: Off
Add-on:                postgresql-clean-54438
```

#### Conversor MarkDown to HTML online
+ https://markdowntohtml.com/
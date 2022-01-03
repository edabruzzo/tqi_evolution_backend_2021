![tqi_evolution_backend_2021](imagens/tqi_banner.jpg)

# Projeto Final TQI backend em parceria com a Digital Innovation One
## Projeto entregue como requisito final para avançar no processo seletivo da TQI - JAVA DEVELOPER

## Emmanuel de Oliveira Abruzzo - Dezembro/2021 - Janeiro/2022
+ Projeto Final TQI - DIGITAL INNOVATION ONE - BOOTCAMP - TQI-JAVA-DEVELOPER - Dez/2021

#### Microserviços integrados num sistema de cadastro de clientes e pedido de empréstimos

#### GITHUB:
+ https://github.com/edabruzzo/tqi_evolution_backend_2021

#### DOCKER HUB
+ https://hub.docker.com/repository/docker/edabruzzo/tqi_evolution_backend_2021

#### LINKEDIN:
+ https://www.linkedin.com/in/emmanuel-abruzzo-8ba80a36/

#### DISCORD - DIO
+ Discord: https://discord.gg/AWxMaerJ

#### DIGITAL INNOVATION ONE
+ https://digitalinnovation.one/

+ #### Video deploy
+ https://youtu.be/6Vd3WYr5r3E
+ https://youtu.be/MM0CQyWEQ7s



### Localização de todos os projetos desenvolvidos no Bootcamp TQI junto à Digital Innvation One

* @edabruzzo_BOOTCAMP_TQI_DIGITAL_INNOVATION_ONE
+ https://github.com/users/edabruzzo/projects/3



### Arquitetura escolhida -  Arquitetura de microserviços orientada a eventos usando Spring Cloud

  ![img.png](imagens/arquitetura_microservices_spring_cloud.png)

  Optamos por modelar nossa aplicação em microserviços para garantir independência de cada funcionalidade do resto da aplicação induz ao baixo acoplamento. Com isso, temos mais facilidade de lidar com questões técnicas, que são fortemente influenciadas pelos requisitos funcionais e não funcionais do negócio que estamos informatizando.
  
  Pretendemos, com isso baixo acoplamento entre os módulos, que solicitam serviços através de chamadas Rest, sendo que cada serviço expõe recursos 

  Precisamos implementar um microsserviço tolerante a falhas, resiliente a integrações defeituosas e capaz de não indisponibilizar toda a aplicação por causa de uma única funcionalidade.

  A opção pela arquitetura de microsserviços se deu também para permitir que nossa aplicação possa 
  escalar horizontalmente de forma fácil e responsiva, permitindo o processamento de várias Threads
  e requisições a diferentes funcionalidades de forma paralela e resiliente a degradações de perfomance 
  pontuais em outros serviços.
  
  Para controle de acesso aos recursos e segurança da aplicação optamos pela implementação de um módulo de segurança para garantir acesso a recursos por:
  * autenticação 
  * papeis 


### Spring boot x Spring Cloud compatibility

![img.png](imagens/compatibilidade_spring_cloud.png)
Fonte: https://spring.io/microservices


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


### Criação de um Microservice para Solicitações de Empréstimo usando Redis in-memory Database
Nós utilizamos o serviço de gerenciamento de clientes como ponto de entrada da solicitação de empréstimos.
A partir dele, após avaliações de regras de negóio básicas, o serviço de solicitação e avaliação de empréstimos
é chamado para persistir em memória a solicitação, e disparar outros serviços externos de avaliações sobre 
a solicitação. A solicitação permanece persistida em memória, num banco REDIS enquanto é processada por outros serviços
e, caso aprovada, o serviço de solicitaçaõ e avaliação chama o serviço de gerenciamento de empréstimo para persistir 
o empréstimo somente no caso de aprovação, pois ele passa a ser gerenciado pelo serviço de empréstimo,
que faz uso de um banco relacional. A solicitação de empréstimo fica em memória, enquanto é avaliada por um sistema de gerenciamento de risco de crédito interno
e são realizadas consultas aos sistemas de proteção ao crédito via chamadas  aos seus respectivos WebServices utilizando o CPF do cliente
   
Um DTO Emprestimo é passado via chamada REST com o método POST utilizando a API de solicitação de empréstimos já registrada no Eureka Discovery Server, 
que irá persistir a solicitação no Redis, enquanto ela está sendo avaliada


##### O importante aqui é perceber o caráter potencialmente transitório de uma solicitação de empréstimo


#### Referência do Redis Spring 

#### Importante que os campos da entidade SolicitacaoEmprestimo sejam indexados para que possam ser objeto de queries na camada Repository
+ https://docs.spring.io/spring-data/data-redis/docs/current/reference/html/#tx



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
é impraticável, daí a mecessidade de um Config Server. Um servidor de configuração é o lugar central para definir as configurações dos serviços

Todas as configurações dos microsserviços devem ficar externalizadas e centralizadas, além de serem versionadas

Foi criado um repositório específico para configurações dos microsserviços do projeto:
+ https://github.com/edabruzzo/tqi_evolution_backend_2021_microservices_configuration_repo



### Ribbon - Client Side Load Balancing

Com a anotação do Ribbon LoadBalance no RestTemplate responsável por invocar o microservice Emprestimo, 
a cada requisição, o Ribbon rotaciona do lado do cliente para uma instância diferente do microservice de Emprestimo, através 
de um algoritmo de load balancing.

Vide documentação do Ribbon:
+https://cloud.spring.io/spring-cloud-static/spring-cloud-netflix/2.2.0.M3/reference/html/#customizing-the-default-for-all-ribbon-clients



### Integração entre Eureka Server | Ribbon | Declarative REST Clients usando: Feign

Referência
+https://cloud.spring.io/spring-cloud-netflix/multi/multi_spring-cloud-feign.html

#### Declarative REST Client: Feign
Optamos inicialmente por fazer nossas chamadas para o serviço de solicitação e avaliação de empréstimos 
injetando no serviço que faz a chamada o RestTemplate (@Bean gerenciado anotado com @LoadBalanced), 
que já possuía o benefício do balanceamento de carga do lado do cliente pelo Ribbon 

No serviço cliente que invoca o outro microsserviço, fazendo um POST com o DTO Solicitação de empréstimo, 
usamos o próprio EurekaDiscoveryClient para perguntar a ele se as instâncias do outro microsserviço estavam 
com status UP. Em caso de falha no serviço invocado, nós lançamos uma exceção customizada indicando um problema
de Infraestrutura e utilizamos o Hystrix para invocar um método de Fallback.


Decidimos, porém utilizar o Feign como web server client para fazer a chamada de forma declarativa e aí não 
precisamos mais fazer o POST para o outro microsserviço de forma programática com RestTemplate 

O Feign trabalha com anotações próprias e com anotações da implementação JAX-RS do JAVAEE.

Ele se integra de forma muito fácil com o Ribbon e com o Eureka para fazer chamadas para outros microsserviços
e com provimento de balanceamento de carga.

7.1 How to Include Feign
To include Feign in your project use the starter with group org.springframework.cloud and artifact id spring-cloud-starter-openfeign. See the Spring Cloud Project page for details on setting up your build system with the current Spring Cloud Release Train.

Example spring boot app

@SpringBootApplication
@EnableFeignClients
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
StoreClient.java.

@FeignClient("stores")
public interface StoreClient {
@RequestMapping(method = RequestMethod.GET, value = "/stores")
List<Store> getStores();

    @RequestMapping(method = RequestMethod.POST, value = "/stores/{storeId}", consumes = "application/json")
    Store update(@PathVariable("storeId") Long storeId, Store store);
}
In the @FeignClient annotation the String value ("stores" above) is an arbitrary client name, which is used to create a Ribbon load balancer (see below for details of Ribbon support). You can also specify a URL using the url attribute (absolute value or just a hostname). The name of the bean in the application context is the fully qualified name of the interface. To specify your own alias value you can use the qualifier value of the @FeignClient annotation.

The Ribbon client above will want to discover the physical addresses for the "stores" service. If your application is a Eureka client then it will resolve the service in the Eureka service registry. If you don’t want to use Eureka, you can simply configure a list of servers in your external configuration (see above for example).


### Roteador e filtro Zuul Netflix API Gateway 

![img.png](imagens/gateway.png)
Fonte: https://cloud.spring.io/spring-cloud-gateway/reference/html/

Referência:
+ https://cloud.spring.io/spring-cloud-netflix/multi/multi__router_and_filter_zuul.html

Optamos por utilizar o Spring Cloud Netflix Zuul como proxy, filtro e roteador de nossa aplicação.

Consideramos que num cenário de uma API reativa, utilizando Reactor, Netty e WebFlux, o Spring Gateway 
seria a opção mais apropriada, mas como estamos trabalhando com soluções síncronas, como o Spring Data,
o projeto Zuul foi nossa escolha.

O funcionamento de um Gateway Proxy, basicamente segue a seguinte lógica:

Os microsserviços não são expostos diretamente na internet. Ao invés disso, posicionamos um servidor proxy 
para receber os requests. Quando um cliente faz um request para um serviço, o Gateway 
possui um tratamento para as rotas dos microsserviços mapeadas pelo Eureka Server.

Com base nesse mapeamento, o Gateway conhece as rotas para os serviços registrados no Eureka server.
E ele sabe, com base nisso, determinar para qual rota de microserviço o request recebido será enviado.

Este tratamento é executado de acordo com uma cadeia de filtros,específica para o request.
Assim, podem ser executadas lógicas em diversos momentos da aplicação da cadeia de filtros.

O Zuul é um gateway, um proxy embarbacado baseado na JVM e desenvolvido pela Netflix, que o utiliza de forma embarcada
para roteamento e balanceamento de carga do lado do cliente.



### Autenticação e autorização de acesso a operações

Referências: 

+ https://www.baeldung.com/spring-cloud-security
+ https://medium.com/@mool.smreeti/microservices-with-spring-boot-authentication-with-jwt-and-spring-security-6e10155d9db0


Quando pensamos em autorização em um contexto de arquitetura de microsserviços, precisamos lembrar 
que os estados e contextos de execução entre microsserviços e instâncias de um mesmo microsserviço
não são compartilhados, sendo que cada qual está rodando em um ambiente isolado dos demais.

Diferentemente de um monolito rodando num servidor de aplicação (Jboss/Wildfly ... )ou mesmo num uber jar, 
não temos como compartilhar o estado da sessão de usuário durante a realização de uma transação, 
justamente porque a transação agora está quebrada em vários serviços que rodam em ambientes isolados.

Isso nos impõe a necessidade de pensar no uso de um serviço externo ou criar nosso próprio servidor 
de autenticação para ser utilizado por todos os microsserviços necessários para que se complete uma transação 
ou um serviço completo de nosso sistema.

Diante disso, implementamos um servidor de autenticação em nosso sistema, utilizando Spring Cloud Security,
o qual implementa autenticação baseada em OAuth2 SSO (Single Sign-ON) com suporte para 
a troca de tokens entre Resource Servers de nosso sistema, assim como a configuração de 
autenticação downstream utilizando o nosso Gateway Zuul proxy, embarcado na JVM.



Para que haja a integração entre o Spring Security e o Spring Cloud OAuth 2.0, precisamos criar uma classe de configuração 
que herda do WebSecurityConfigurerAdapter, injetando os beans gerenciados 
AuthenticatorManager e UserDetailService do SpringSecurity injetados no Adapter do Spring Cloud OAuth2: AuthorizationServerConfigurerAdapter
A integração entre eles é feita via método configure do Adapter.

É necessário também anotar a classe principal do nosso servidor de autenticação com @EnableAuthorizationServer e @EnableResourceServer



A autenticação de usuário **e microsserviço** é efetuada através de trocas de tokens de acesso criptografados JWT. 







#### OAuth 2.0
Referência:
+ https://backstage.forgerock.com/knowledge/kb/article/a45882528


### Agregação de logs com ElasticSearch

![Diagrama Elastic Search Stack](imagens/diagram-elastic-stack.png)
Fonte: https://cassiomolin.com/2019/06/30/log-aggregation-with-spring-boot-elastic-stack-and-docker/




### Tracing operações de negócio com Spring Cloud Sleuth e Logback

![Diagrama Tracing_Sleuth](imagens/trace-id.jpg)
Fonte: https://docs.spring.io/spring-cloud-sleuth/docs/current-SNAPSHOT/reference/html/getting-started.html#getting-started

Em arquiteturas de microsserviço, uma simples operação de negócio pode ativar uma 
longa cadeia downstream de chamadas entre serviços.

O rastreamento de logs e debug de aplicações em arquiteturas como esta é um grande desafio, 
uma vez que, diferentemente de aplicações monolíticas hospedadas em servidor de aplicação, 
os logs não ficam centralizados, mas ao invés estão distribuídos por muitos servidores e instâncias desses servidores.

Isso dificulta o acompanhamento e rastreabilidade das requisições
Para unificar os logs, precisamos de agregadores de log
Como implementação de um agregador, temos o Papertrail, o ElasticSearch com o Kibana como UI
, dentre outras opções aí no mercado

Usamos a biblioteca Logback para gerar e enviar os logs ao agregador
O Logback possui um appender, que possibilita o envio dos logs
Para acompanhar uma transação nos logs, usamos uma correlation-id
A correltation-id é um identificador da transação, que é passada de requisição pra requisição
Dessa forma, podemos entender quais requisições fazem parte da mesma transação

A biblioteca Spring Sleuth, que é usada para gerar a correlation-id
O Spring Cloud Sleuth é uma solução de tracing distribuído para 
aplicações Spring Cloud, adicionando um forma de rastrear logs, por meio de trace e spans. 


Referência:
+ https://cloud.spring.io/spring-cloud-sleuth/
+ https://docs.spring.io/spring-cloud-sleuth/docs/current-SNAPSHOT/reference/html/using.html#using


### RODANDO NOSSA APLICAÇÃO EM CONTAINER
![img.png](imagens/docker_containers.png)
Fonte e referência: + https://cassiomolin.com/2019/06/30/log-aggregation-with-spring-boot-elastic-stack-and-docker/

Nossa aplicação está rodando de forma totalmente dockerizada em uma rede bridge 
 
+ https://github.com/cassiomolin/log-aggregation-spring-boot-elastic-stack/blob/master/docker-compose.yml
+https://www.baeldung.com/dockerizing-spring-boot-application
+https://developer.okta.com/blog/2019/02/28/spring-microservices-docker

Necessário configurar um Dockerfile na raiz de cada Microsserviço ou em outro local centralizado, caso 
deseje.

Dockerfile
```
# spring.io/guides/gs/spring-boot-docker

FROM openjdk:11-jre
MAINTAINER Emmanuel de Oliveira Abruzzo <emmanuel.oliveira3@gmail.com>
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java -jar app.jar"]

```
Executar o comando Maven para construir os *.jar de cada projeto:


E, após, construir a imagem Docker de cada projeto:

```
docker build -t 
```


É possível usar o próprio Maven para construir nossas imagens docker no momento de buildar cada projeto, 
através do plugin do Docker para o Maven construído pelo Spotify.


```
docker logout
```
Logar no docker hub a partir da linha de comando:


```shell

password=***********
usuario=************

echo $password | docker login --username $usuario --password-stdin

for diretorio_projeto in ./tqi_evolution_backend_2021; 
  
  do cd $diretorio_projeto && mvn clean package  && docker build -t edabruzzo/tqi_evolution_backend_2021/$diretorio_projeto:0.0.1-SNAPSHOT ./$diretorio_projeto;
  
done;


```
Ou utilizando o docker-compose na raiz do projeto a partir do docker-compose.yml:

```shell
cd ~/IdeaProjects/tqi_evolution_backend_2021/
docker-compose up -d

```


### Circuit Breaker com Hystrix

![img.png](imagens/circuitBreaker_Hystrix.png)

Estamos usando o Hystrix como Circuit Breaker para fechamento de circuito de modo a evitar 
que uma requisição de empréstimo feita pelo usuário no momento em que o serviço de empréstimo, 
ou banco de dados, ou outro serviço(s) estiver(em) fora ou com problema de performance seja executada 

O Hystrix direciona a requisição para um método fallback, que devolve um EmprestimoDTO vazio para o cliente.

O Hystrix fica tentando a requisição para o verdadeiro serviço a cada 5 segundos, aguardando o 
serviço voltar à normalidade.

O Circuit Breaker executa o processamento da requisição numa thread separada quando o tempo limite
(default de 1s) é excedido na requisição principal para o serviço, repassando a execução para 
o método Fallback que retorna um DTO vazio.


Nós também implementamos um field para track do status da transação de solicitação do empréstimo 
a cada evento processado (processamento, avaliação, aprovaçção, reprovação, falha ao salvar por problemas de infra)

O Hystrix nos permite capturar um Fallback, checar o estado desse objeto DTO e, a depender do estado,
podemos devolver ao cliente o estado da solicitação (autorizada, reprovada, consolidada no serviço de gerenciamento 
de empréstimos), ou podemos simplesmente reprocessar a solicitação nos casos de erro ao salvar 
em memória por problemas de Infra (serviço ou banco de dados fora do ar)

Isso nos permite garantir a realização de todos os passos necessários e ter uma estratégia de
recuperação de erros em tempo de execução.


#### Importante !

Em cenários com um volume muito grande de requisições de usuários, essa alocação de Threads de fallback para 
tratar problemas de performance pode gerar um gargalo no sistema, na alocação de Threaads apenas para um serviço.

Esse gargalo precisa ser tratado !!!

Uma forma de tratar esse cenário é com uma técnica chamada Bulkhead.





### Bulkhead

Essa técnica é inspirada na prevenção de danos em cascos de navio, que são compartimentados e,
em caso de dano, apenas compartimentos pequenos são inundados, evitando o afundamento do navio.

A ideia é alocar grupos de Threads para cada serviço, a fim de evitar que apenas um serviço 
tenha muitas Threads alocadas a ponto de faltarem Threads para outros serviços.

Poder agrupar e alocar grupos de threads para processamentos diferentes.
Dessa forma, uma das chamadas de um microsserviço, que sofre degradação de performance, 
não indisponibiliza todas as outras chamadas do mesmo microsserviço







## Endpoints das APIs expostos pelos microsserviços - testes executados com POSTMAN
* https://web.postman.co/workspace/My-Workspace~728c0cf1-f562-47b6-8f38-800bcfb65bad

  Os testes da API foram realizados utilizando o Postman e abaixo estão as chamadas via CURL para testes

  Criamos arquivos de testes bash scripts com chamadas CURL para as APIs. 
* 
* Na raiz do projeto, executar:

```shell
sh testarChamadasApisMicrosservicos.sh

```


#### EXPLORAÇÃO DE CONCEITOS DE ORIENTAÇÃO A OBJETO

    O projeto explora também herança na criação de Business Exceptions


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
docker run --name tqi_evolution_backend_2021 -d -p 5432:5432 -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=tqi_evolution_backend_2021 postgres

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

psql -U postgres -d tqi_evolution_backend_2021 
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

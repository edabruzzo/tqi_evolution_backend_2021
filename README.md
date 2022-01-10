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



### Localização de todos os projetos desenvolvidos no Bootcamp TQI junto à Digital Innvation One

* @edabruzzo_BOOTCAMP_TQI_DIGITAL_INNOVATION_ONE
+ https://github.com/users/edabruzzo/projects/3



### Arquitetura escolhida -  Arquitetura de microserviços orientada a eventos usando Spring Cloud

  ![img.png](imagens/arquitetura_microservices_spring_cloud.png)

************************************************************************************************************************
##### Mesmo optando por construir o sistema utilizando uma arquitetura de microsserviços com Spring Cloud
##### construímos também um Sistema monolítico MVC, além de quebrar o monolito em microsserviços

* Github do projeto monolítico em MVC:
+ https://github.com/edabruzzo/monolito_mvc_sistema_controle_emprestimos
************************************************************************************************************************

  Optamos por modelar nossa aplicação em microserviços para garantir independência de cada funcionalidade do resto da aplicação induz ao baixo acoplamento. Com isso, temos mais facilidade de lidar com questões técnicas, que são fortemente influenciadas pelos requisitos funcionais e não funcionais do negócio que estamos informatizando.
  
  Pretendemos, com isso baixo acoplamento entre os módulos, que solicitam serviços através de chamadas Rest, sendo que cada serviço expõe recursos 

  Precisamos implementar um microsserviço tolerante a falhas, resiliente a integrações defeituosas e capaz de não indisponibilizar toda a aplicação por causa de uma única funcionalidade.

  A opção pela arquitetura de microsserviços se deu também para permitir que nossa aplicação possa 
  escalar horizontalmente de forma fácil e responsiva, permitindo o processamento de várias Threads
  e requisições a diferentes funcionalidades de forma paralela e resiliente a degradações de perfomance 
  pontuais em outros serviços.
  
  Para controle de acesso aos recursos e segurança da aplicação optamos pela implementação de um módulo de segurança para garantir acesso a recursos por:
  * autenticação 
  * papeis (roles, authorities)
  * 
Utilizamos Thymeleaf integrado com Spring Security para permissão de visualização de componentes 
html e execução de ações no sistema de acordo com roles de usuaŕios


#### Tela Login
![img.png](imagens/telaLoginEmail.png)


#### Tela solicitação de empréstimo
![img.png](imagens/telaSolicitacaoNovoEmprestimo.png)


#### Tela de cadastro e listagem de clientes
![img.png](imagens/tela_cadastro_lista_clientes.png)
![img_1.png](imagens/tela_cadastro_lista_clientes_1.png)









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

Para uma discussão mais aprofundada sobre Load Balancing
+ https://spring.io/blog/2020/03/25/spring-tips-spring-cloud-loadbalancer

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


#### Spring security method level 
Referência
+ https://www.baeldung.com/spring-security-method-security

O Spring Security possui suporte a autorização de acesso no nível dos métodos nas camadas service, controller, ... 
Nós podemos adicionar restrições na execução de métodos, de acordo com roles dos usuários.




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
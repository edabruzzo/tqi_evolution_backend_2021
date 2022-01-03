FROM openjdk:11
MAINTAINER Emmanuel de Oliveira Abruzzo <emmanuel.oliveira3@gmail.com>
ARG CONTEXT=./zuul_gateway
ARG JAR_FILE=./${CONTEXT}/target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java -jar app.jar"]
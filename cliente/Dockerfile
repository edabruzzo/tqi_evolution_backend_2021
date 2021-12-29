# spring.io/guides/gs/spring-boot-docker

FROM openjdk:11-jre
MAINTAINER Emmanuel de Oliveira Abruzzo <emmanuel.oliveira3@gmail.com>
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java -jar app.jar"]



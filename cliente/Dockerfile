FROM openjdk:11-jre
MAINTAINER Emmanuel de Oliveira Abruzzo <emmanuel.oliveira3@gmail.com>

web: java -Dserver.port=$PORT -jar target/project_cities_api-0.0.1-SNAPSHOT.jar -cp br.com.digitalinnovation.abruzzo.project_cities_api.ProjectCitiesApiApplication

ENTRYPOINT ["java","-cp","app:app/lib/*","-Dspring.profiles.active=docker","br.com.abruzzo.ClienteApplication"]

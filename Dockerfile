FROM openjdk:11-jre-slim

WORKDIR /app

COPY target/*.jar /app/livraria-api.jar

EXPOSE 8080

CMD java -XX:+UseContainerSupport -jar livraria-api.jar
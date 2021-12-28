FROM openjdk:17-alpine

WORKDIR opt/server2
COPY ./target/message-service-0.0.1-SNAPSHOT.jar server2.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "server2.jar"]
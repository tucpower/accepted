FROM openjdk:17-jdk-alpine
LABEL authors="ngiannopoulos"
COPY target/stoiximan-0.0.1-SNAPSHOT.jar stoiximan-1.0.0.jar
ENTRYPOINT ["java", "-jar", "stoiximan-1.0.0.jar"]
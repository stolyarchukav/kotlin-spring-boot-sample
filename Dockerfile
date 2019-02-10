FROM openjdk:8-jdk-alpine as build
RUN ./gradlew clean build

COPY build/libs/*.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
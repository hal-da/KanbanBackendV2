# Build stage
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml /app
COPY checkstyle.xml /app
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn -f /app/pom.xml clean verify

# Run stage
FROM openjdk:21-jdk-slim
COPY --from=build /app/target/spring-rest-backend-0.0.1.jar /usr/local/lib/app.jar
EXPOSE 8443
ENTRYPOINT ["java", "-jar", "/usr/local/lib/app.jar"]
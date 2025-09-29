# Dockerfile para Hot-Reload com Spring Boot e Maven
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src

CMD ["mvn", "spring-boot:run", "-DskipTests"]
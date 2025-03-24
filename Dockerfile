FROM openjdk:21-ea-17-jdk-slim AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN apt-get update && \
    apt-get install -y maven && \
    apt-get clean;
RUN mvn clean package -DskipTests

EXPOSE 8080
FROM maven:3.8.4-openjdk-17-slim AS build

WORKDIR /app

COPY pom.xml .
COPY .mvn .mvn

RUN mvn dependency:go-offline

COPY src ./src

CMD ["mvn", "spring-boot:run", "-Dspring.devtools.livereload.enabled=true"]

# Simplified Transactions

this project is used to simulate a transaction service in a simplified way

## 🚀 How to run

1. Clone the repository
2. This project uses docker and docker compose, make sure you have them installed
3. Run the following command to start the services:
   ```bash
   docker compose up --build
   ```
4. The services will be available at the following URLs:
   - API: http://localhost:8080
   - PostgreSQL: http://localhost:5432

## 🛠️ Technologies
- Java 17
- Spring Boot
- PostgreSQL
- Docker

## 🧪 Testing
- Unit tests are written using JUnit 5 and Mockito
- you can test it in the following way:
   - run the following command to run the tests:
     ```bash
     ./mvn clean test
     ```
   - or if you prefer to use docker, you can run
     ```bash
     docker compose --profile test up --abort-on-container-exit --build
     ``````

## 📚 Documentation
(SpringDocs) API Documentation is available at [docs](http://localhost:8080/swagger-ui.html)
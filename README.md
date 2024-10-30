# **Customer API - Java Spring Boot**

This project implements a **Customer API** using **Java Spring Boot**. The API provides **CRUD operations** for managing customer records, with **MySQL** as the database. It also includes **logging** with Logback, observability with **Spring Boot Actuator**, and **integration tests** for validation. The API can be containerized using **Docker**.

## **Setup Instructions**
`compose.yaml` file is provided to start both the MySQL database and the Customer API as services.
1. Build the Docker Image:
```bash
docker-compose build
```
2. Start the services:
```bash
docker-compose up
```
3. Access the API:
- Visit `http://localhost:8080/api/customers` to interact with the API.

If you want to run the API without using Docker, you need to set the environment variables defined in `.env-sample` file.

## **Observability**
- **Logback** is used as the logging framework. Logs are printed to the console.
- **Spring Boot Actuator** exposes health, metrics, and loggers endpoints.

## **Testing Instructions**
```bash
./mvnw test
```

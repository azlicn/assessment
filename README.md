# Backend Assessment

Backend assignment - A simple CRUD Spring Boot application
## Installation

Ensure that you have [Docker](https://docs.docker.com/get-docker/) and [Java 11](https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html) installed in your
environment.

```bash
# This will spin up the MSSQL Server container
docker compose -f docker/docker-compose.yml up
```
Once done, make sure to create a database called TESTDB, before proceed to the next step

## Build & Run

You may run the project using your preferred IDE or using the following command

```bash
# This run the application
mvn spring-boot:run
```

## Development

The project is currently running on your localhost.

* Navigate to ``http://localhost:8080`` for the Spring Boot application.
* For MSSQL Server, please use [DBeaver](https://dbeaver.io/) to interact with the
  database.

## Test

You may run the tests using your preferred IDE or using the following command:

```bash
mvn test
```

## Notes

* Navigate to [Swagger Document](http://localhost:8080/swagger-ui/index.html) for API Documentation.
* Postman Collection is under postman folder
* Log file is under logs folder
 

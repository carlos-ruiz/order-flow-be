## Orders Flow Project

Orders Flow Project is an Spring Boot application to provide an API for interacting with the application and do all business logic related to the application. Another client application should implement this functionality.

### Getting Started
#### Required dependencies
- JDK 21
- Maven
- Docker

Create a `.env` file in the root path of your application with the following variables
1. MYSQL_DATABASE: Which is the name of the database used by your application
2. MYSQL_PASSWORD: The password for the database

`NOTE: these variables could be whatever you want, the first time you run the application, the database will be created using those values`

We have a `docker-compose.yml` file in the root path of your application with the MYSQL image we need to host the Database. So we would need to have a Docker installed.

### Steps to Running the application

Run the following commands from the root path of your application

1. Execute the docker-compose.yml, command `docker compose up -d`
2. Install Maven dependencies, command `./mvnw clean install`
3. Run the application, command `./mvnw spring-boot:run`


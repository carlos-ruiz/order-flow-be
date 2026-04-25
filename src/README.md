## Orders Flow — Backend API

Spring Boot REST API for managing orders, customers, sellers, platforms, and order items. Designed to be consumed by a separate client application.

### Tech Stack

- Java 21
- Spring Boot 3.4.3
- Spring Data JPA + MySQL 9.2
- Lombok
- SpringDoc OpenAPI (Swagger UI)
- JaCoCo (80% line/branch coverage enforced)
- Docker + Docker Compose

### Prerequisites

- JDK 21
- Maven
- Docker

### Local Setup

**Step 1.** Create a `.env` file in the project root:

```env
MYSQL_ROOT_PASSWORD=your_root_password
MYSQL_DATABASE=your_database_name
```

**Step 2.** Start the database:

```bash
docker compose up -d
```

**Step 3.** Run the application:

```bash
./mvnw spring-boot:run
```

The app starts on `http://localhost:8080`. Swagger UI is available at `http://localhost:8080/swagger-ui.html`.

### Running with Docker (full stack)

Builds the app image and starts both MySQL and the API together:

```bash
docker compose up --build
```

This uses the `docker` Spring profile, which reads database config from the environment variables defined in `.env`.

### Running Tests

```bash
./mvnw clean test
```

JaCoCo enforces a minimum of **80% line and branch coverage**. The build will fail if coverage drops below this threshold.

### Spring Profiles

| Profile  | Used when                                       |
|----------|-------------------------------------------------|
| `local`  | Running directly on your machine with a local DB |
| `docker` | Running inside Docker Compose                   |
| `prod`   | Deployed to AWS Elastic Beanstalk               |

### API Resources

| Resource    | Base path      |
|-------------|----------------|
| Customers   | `/customers`   |
| Orders      | `/orders`      |
| Order Items | `/order-items` |
| Sellers     | `/sellers`     |
| Platforms   | `/platforms`   |
| Status      | `/status`      |

### CI/CD

GitHub Actions pipeline on `.github/workflows/deploy.yml`:

- **All pushes to `main` or `development`**: runs tests and builds the JAR
- **Pushes to `main` only**: deploys to AWS Elastic Beanstalk (`prod` environment) via OIDC role assumption — no long-lived AWS credentials stored
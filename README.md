# Device for All

Device for All is a REST API for managing institutional devices, users, departments, and device loans. It is built with Spring Boot, Spring Data JPA, MySQL, JWT authentication, permission-based authorization, and Flyway database migrations.

## Current stack

- Java 23 (current Maven configuration)
- Spring Boot 3.2.4
- Spring MVC
- Spring Data JPA and Hibernate
- Spring Security
- JSON Web Tokens (JJWT)
- MySQL
- Flyway
- MapStruct and Lombok
- Springdoc OpenAPI
- Docker Compose
- JUnit, Mockito, Spring Security Test, and H2 for tests

## Architecture

The application follows a layered structure:

```text
Controller -> Service -> Repository -> Database
```

The source code is organized by domain:

- `auth`: login, JWT handling, roles, permissions, and security configuration
- `users`: users, user profiles, and departments
- `devices`: devices and device types
- `loans`: loans and loan comments
- `common`: shared API responses, exception handling, and security components

DTOs and MapStruct mappers keep the API models separate from the JPA entities. Shared exception handlers provide consistent authentication, authorization, validation, and resource error responses.

## Authentication and authorization

The API uses stateless JWT authentication.

- `POST /auth/login` is public.
- All `/api/**` routes require an authenticated user.
- Controller methods use permission-based authorization with `@PreAuthorize`.
- User passwords are stored as BCrypt hashes.
- JWTs are sent with the `Authorization: Bearer <token>` header.
- Tokens currently expire after 24 hours.
- Disabled users cannot authenticate or use an existing token.

### Login

Request:

```http
POST /auth/login
Content-Type: application/json

{
  "username": "your-username",
  "password": "your-password"
}
```

Successful response:

```json
{
  "token": "<jwt>"
}
```

Use the returned token for protected requests:

```http
Authorization: Bearer <jwt>
```

The initial Flyway migration creates the `ADMIN`, `SUPERVISOR`, `STAFF`, and `BORROWER` roles and their permissions. It does not create a default user. A user with a BCrypt password hash and an assigned role must exist before login can succeed.

## API routes and required permissions

| Resource | Operations | Required permission |
| --- | --- | --- |
| `/api/devices` | GET collection or item | `DEVICE_READ` |
| `/api/devices` | POST | `DEVICE_CREATE` |
| `/api/devices/{id}` | PUT | `DEVICE_UPDATE` |
| `/api/devices/{id}` | DELETE | `DEVICE_RETIRE` |
| `/api/loans` | GET collection | `LOAN_READ_ALL` |
| `/api/loans/{id}` | GET | `LOAN_READ_OWN` |
| `/api/loans` and `/api/loans/{id}` | POST, PUT, DELETE | `LOAN_CREATE_OWN` |
| `/api/loan-comments` | GET collection or item | `LOAN_READ_ALL` |
| `/api/loan-comments` and `/api/loan-comments/{id}` | POST, PUT, DELETE | `LOAN_CREATE_OWN` |
| `/api/users` | GET, POST, PUT, DELETE | `USER_READ` |
| `/api/departments` | GET, POST, PUT, DELETE | `USER_READ` |
| `/api/roles` | GET collection or item | `ACCESS_MANAGE` |
| `/api/permissions` | GET collection or item | `ACCESS_MANAGE` |

These entries describe the permissions currently declared by the controllers.

## Database migrations

Flyway is the only mechanism used to create and update the local MySQL schema. Hibernate validates the resulting schema with:

```properties
spring.jpa.hibernate.ddl-auto=validate
```

Migration files are stored in:

```text
src/main/resources/db/migration
```

They follow Flyway's naming convention:

```text
V{version}__{description}.sql
```

For example:

```text
V2__add_device_purchase_date.sql
```

Flyway applies pending migrations automatically when the application starts and records them in `flyway_schema_history`.

Once a migration has been applied, do not edit it. Create a new migration with the next version instead.

## Local configuration

Create `src/main/resources/application.properties` with the local datasource and JWT settings. This file is ignored by Git.

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/device_loans
spring.datasource.username=<database-user>
spring.datasource.password=<database-password>

spring.jpa.hibernate.ddl-auto=validate

jwt.key=<base64-encoded-signing-key>
```

The datasource credentials must match the local MySQL configuration in `docker/docker-compose.yml`.

## Running locally

### Prerequisites

- A JDK compatible with the Java version configured in `pom.xml`
- Docker with Docker Compose

### 1. Start MySQL

From the project root:

```powershell
docker compose -f docker/docker-compose.yml up -d
```

### 2. Start the application

```powershell
.\mvnw.cmd spring-boot:run
```

During startup, Flyway applies pending migrations and Hibernate validates the schema. The API is then available at:

```text
http://localhost:8080
```

### 3. Stop MySQL

```powershell
docker compose -f docker/docker-compose.yml down
```

## OpenAPI

Springdoc is configured with these paths:

- OpenAPI JSON: `http://localhost:8080/v3/api-docs`
- Swagger UI: `http://localhost:8080/swagger-ui.html`

The current security configuration permits only `POST /auth/login` publicly, requires authentication for `/api/**`, and denies other routes. The OpenAPI and Swagger paths therefore need to be added to the security allowlist before the browser-based documentation can be accessed.

## Tests

Run the complete test suite with:

```powershell
.\mvnw.cmd test
```

The test profile uses an in-memory H2 database with Hibernate `create-drop`. Flyway is disabled for that profile because the production migration contains MySQL-specific SQL.

## Current scope

The project currently supports local development with Dockerized MySQL. Production deployment, externalized secrets, and production database migration procedures are planned separately.

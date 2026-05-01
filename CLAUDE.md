# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Vehicle Service is a Micronaut 4.10.13 microservice written in Groovy, part of the CarStore project. It uses PostgreSQL with Hibernate JPA, JWT-based security (bearer authentication), and exposes a Swagger UI for API documentation.

## Build & Run Commands

```bash
# Build the project
./gradlew build

# Run the application
./gradlew run

# Run all tests
./gradlew test

# Run a single test class
./gradlew test --tests "br.com.carstore.VehicleServiceTest"

# Run a single test method
./gradlew test --tests "br.com.carstore.VehicleServiceTest.testItWorks"

# Clean build
./gradlew clean build

# Build native Docker image
./gradlew dockerBuildNative
```

## Architecture

- **Framework**: Micronaut 4.10.13 with Netty runtime
- **Language**: Groovy (with `@CompileStatic` used on entry point)
- **Package**: `br.com.carstore`
- **Java target**: 25
- **Test framework**: JUnit 5 with `@MicronautTest` annotations, Mockito for mocking
- **Database**: PostgreSQL via Hibernate JPA + HikariCP connection pool. Schema auto-updated via `hbm2ddl.auto=update` (prod), `create-drop` (test)
- **Test Resources**: Micronaut Test Resources plugin manages test database containers automatically
- **Security**: JWT bearer token authentication. Signing secret configured via `JWT_GENERATOR_SIGNATURE_SECRET` env var
- **API docs**: OpenAPI + Swagger UI served at `/swagger-ui/**`
- **Serialization**: Micronaut Serialization with Jackson

## Layered Architecture

Standard Controller → Service → Repository pattern under `br.com.carstore`:

- **Controllers** (`controller/`): HTTP endpoints. `@Secured(IS_AUTHENTICATED)` by default; `UserController` uses `IS_ANONYMOUS` for user registration
- **Services** (`service/`): Business logic with `@Singleton`. `UserService` hashes passwords with BCrypt before saving
- **Repositories** (`repository/`): Micronaut Data interfaces extending `CrudRepository`. Custom query methods follow Micronaut Data naming conventions (e.g., `findByUsername`)
- **Domain** (`domain/`): JPA entities annotated with `@Entity`, `@Serdeable`, and `@CompileStatic`. Tables: `vehicle` (model, brand, license_plate) and `app_user` (username, password)
- **Auth** (`auth/`): `AuthenticationProviderUserPassword` implements `HttpRequestAuthenticationProvider` — looks up user by username, verifies BCrypt password hash

## Authentication Flow

1. Register user via `POST /users/` (anonymous access)
2. Obtain JWT token via `POST /login` (Micronaut Security built-in endpoint, accepts `{"username": "...", "password": "..."}`)
3. Use `Authorization: Bearer <token>` header for authenticated endpoints (e.g., `POST /vehicles/`, `GET /vehicles/{id}`)

## Key Conventions

- Controllers are annotated with `@Controller` and use Micronaut HTTP annotations (`@Get`, `@Post`, etc.)
- Domain classes use `@CompileStatic` and `@Serdeable` for Micronaut serialization
- Dependencies are injected via constructor injection (no `@Inject` on fields)
- Configuration is in `.properties` format (not YAML)
- Test-specific config overrides live in `src/test/resources/application-test.properties`
- The project uses Gradle wrapper (`./gradlew`) — do not use a system Gradle installation
- DB connection, credentials, and JWT secret are configurable via env vars: `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`, `JWT_GENERATOR_SIGNATURE_SECRET`

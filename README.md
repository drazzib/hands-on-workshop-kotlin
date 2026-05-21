# Hands-On workshop on Kotlin & Spring Boot

## Purpose

This repository is a hands-on workshop.
It shows how to build maintainable backend services with **Kotlin** and **Spring Boot**.

The training is split into progressive modules. You can start from Kotlin syntax only, then move to Spring, coroutines, and advanced error modeling.

## What You Will Work On

- A Kotlin Spring Boot REST API for a pet shop
- HTTP service-to-service communication
- Database access
- Local development with Docker Compose
- API exploration with Bruno collections

## Learning Path

Recommended order for beginners:

1. `exo0-kotlin` - Kotlin language fundamentals
2. `exo1-spring` - Kotlin + Spring + JPA
3. `exo2-reactive` - Coroutines and non-blocking I/O
4. `exo3-advanced` - Typed errors with Arrow `Either`

## Project Structure

- `master-one-pet-data/`: supporting HTTP service used by the exercises, see [Start dependencies](#start-dependencies) section
- `bruno/`: Bruno collections for manual API testing, see [API Testing with Bruno](#api-testing-with-bruno) section
- `exo0-kotlin/`, `exo1-spring/`, `exo2-reactive/` & `exo3-advanced/` : workshop exercises (one module per exercise). Start here: [Exercise #0: Kotlin syntax](exo0-kotlin/README.md)

## Getting Started

### Prerequisites

You need the following tools installed to run the exercises:

- Java 21 LTS
  > We recommend [SDKMAN](https://sdkman.io/) to manage Java versions.
  > Example installation: `sdk install java 21.0.11-tem`
- Docker & Docker Compose
  > For instance, we have tested with [Rancher Desktop](https://rancherdesktop.io/)

Optional but recommended:

- IntelliJ IDEA (Community is enough)
- Bruno (for manual API calls)

### Verify your setup

Run these commands before starting the workshop:

```bash
java -version
./mvnw -version
docker --version
docker compose version
```

Expected outcome:

- Java version starts with `21`
- Maven wrapper runs without downloading errors
- Docker engine is running and `docker compose` command is available

### Start dependencies

1. Build image for `master-one-pet-data` service:

    ```bash
    docker compose build
    ```

2. Start `master-one-pet-data` (on 8081 port) and `postgresql` (on 5432 port) services:

   ```bash
   docker compose up
    ```

3. Validate they are reachable:

  - `master-one-pet-data`: http://localhost:8081/actuator/health
  - PostgreSQL: exposed on `localhost:5432` (user/password/db: `devsummit`)

### Build and run exercises

1. Build and run the exercise you want to work on (e.g. `exo1-spring`):

    ```bash
    ./mvnw -pl exo1-spring spring-boot:run
    ```

2. The service will be available at `http://localhost:8080/` (for usage with Bruno).

3. You can also run integration tests (will use an ephemeral PostgreSQL database via [TestContainer](https://java.testcontainers.org/)) with:

    ```bash
    ./mvnw -pl exo1-spring verify
    ```

### Suggested session flow

For each exercise:

1. Read the module README
2. Run tests once before changing anything
3. Implement one step
4. Re-run tests
5. Validate behavior in Bruno

## API Testing with Bruno

Bruno collections are available in `bruno/`.

1. [Install Bruno](https://www.usebruno.com/)
2. Import the `bruno/` directory
3. Run the provided requests against your local services

## Troubleshooting

- Port already used (`8080`, `8081`, `5432`): stop conflicting apps or change local port mapping.
- Docker dependency startup fails: re-run `docker compose up` and check container health.
- Tests fail with "Could not find a valid Docker environment" error. Check that you have followed [Testcontainer configuration for Rancher Desktop](https://docs.rancherdesktop.io/how-to-guides/using-testcontainers/#configuration) because Testcontainers needs three environment variables to locate the Docker socket and connect to container ports inside the VM

# Hands-On workshop on Kotlin & Spring Boot

## Purpose

This repository is a hands-on workshop.
It shows how to build maintainable backend services with **Kotlin** and **Spring Boot**.

## What You Will Work On

- A Kotlin Spring Boot REST API for a pet shop
- HTTP service-to-service communication
- Database access
- Local development with Docker Compose
- API exploration with Bruno collections

## Project Structure

- `master-one-pet-data/`: supporting HTTP service used by the exercises, see [Start dependencies](#start-dependencies) section
- `bruno/`: Bruno collections for manual API testing, see [API Testing with Bruno](#api-testing-with-bruno) section
- `exo0-kotlin/`, `exo1-spring/`, `exo2-reactive/` & `exo3-advanced/` : workshop exercises (one module per exercise). Start here: [Exercise #0: Kotlin syntax](exo0-kotlin/README.md)

## Getting Started

### Prerequisites

You need the following tools installed to run the exercises:

- Java 21 TLS.
  > We recommend usage use [SDKMAN](https://sdkman.io/) to manage Java versions.
  > You can install Eclipse Temurin with `sdk install java 21.0.11-tem`
- Docker & Docker Compose
  > For instance, we have tested with [Rancher Desktop](https://rancherdesktop.io/)

### Start dependencies

1. Build image for `master-one-pet-data` service:

    ```bash
    docker compose build
    ```

2. Start `master-one-pet-data` (on 8081 port) and `postgresql` (on 5432 port) services:

   ```bash
   docker compose up
    ```

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

## API Testing with Bruno

Bruno collections are available in `bruno/`.

1. [Install Bruno](https://www.usebruno.com/)
2. Import the `bruno/` directory
3. Run the provided requests against your local services

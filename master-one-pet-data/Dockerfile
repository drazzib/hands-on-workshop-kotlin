FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app
COPY . .
RUN --mount=type=cache,sharing=private,target=/root/.m2 \
    ./mvnw -DskipTests --batch-mode --projects master-one-pet-data \
    clean package

FROM eclipse-temurin:21-jre-alpine AS runtime
WORKDIR /app
COPY --from=build /app/master-one-pet-data/target/*.jar app.jar
# See master-one-pet-data/src/main/resources/application.yml for port configuration
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]

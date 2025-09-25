# === Build stage ===
FROM maven:3.9.8-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline -B

COPY src ./src

# === Run stage ===
FROM maven:3.9.8-eclipse-temurin-21

WORKDIR /app

COPY --from=build /app /app

EXPOSE 8080

EXPOSE 5005

CMD ["mvn", "spring-boot:run", "-Dspring-boot.run.fork=false"]

# Spring Boot Template

This is a Spring Boot template that includes a REST API for managing clients, accounts, and movements with comprehensive test coverage and Docker support.

## Features

- **REST API** for client movements management
- **JPQL queries** for complex data retrieval
- **Comprehensive testing** with unit, integration, and end-to-end tests
- **Docker support** for easy deployment
- **GitHub Actions** for continuous integration
- **Exception handling** with custom error responses
- **Data validation** and mapping with MapStruct

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- Docker (optional, for containerized deployment)

### Local Development

#### Clone the repository*
   ```bash
   git clone https://github.com/GermanMT/spring-boot-template
   cd spring-boot-template
   ```

#### Run with your machine Spring Boot

1. **Run the application**
   ```bash
   ./mvnw spring-boot:run
   ```

2. **Access the application**
   - API Base URL: `http://localhost:8080`
   - H2 Console: `http://localhost:8080/h2-console`
   - Swagger UI: `http://localhost:8080/swagger-ui.html`

#### Build and Run with Docker Compose
```bash
docker-compose -f dev/docker-compose.yml up --build
```

### Running Tests

#### Run All Tests
```bash
./mvnw test
```

#### Run Specific Test Categories
```bash
# Unit tests only
./mvnw test -Dtest="*Test"

# Integration tests only
./mvnw test -Dtest="*IntegrationTest"

# Repository tests only
./mvnw test -Dtest="*RepositoryIntegrationTest"
```

## Documentation

For detailed documentation, tutorials, and examples on how to work with Spring Boot, please refer to the official Spring Boot website: [https://spring.io/projects/spring-boot](https://spring.io/projects/spring-boot)

## Community and Support

Join the Spring Boot community to connect with other developers and get support:

- [Spring Boot Forum](https://community.spring.io/forum/spring-boot)
- [Stack Overflow](https://stackoverflow.com/questions/tagged/spring-boot)
- [GitHub Issues](https://github.com/spring-projects/spring-boot/issues)

## License

This Spring Boot template is provided under the [Apache 2.0 license](https://github.com/spring-projects/spring-boot/blob/main/LICENSE.txt).

---

Feel free to customize and adapt this Spring Boot template to suit your needs. Happy coding!

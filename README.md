# sql-loader-kotlin-spring

A lean CLI utility in Kotlin using Spring Boot, Gradle, and JDBC. The tool executes SQL data load scripts from the command line in a minimal container for Kubernetes environments.

## Features

- **Simple CLI**: Execute SQL scripts with a simple command-line interface
- **JDBC-based**: Direct JDBC connections without ORM overhead
- **Transaction Support**: Automatic rollback on errors
- **Minimal Container**: Optimized for small container size and fast startup
- **Test-First**: Comprehensive test coverage with Testcontainers

## Prerequisites

- Java 17 or higher
- Gradle 8.5+ (or use the included wrapper)

## Quick Start

### Build the Project

```bash
./gradlew build
```

### Run Tests

```bash
./gradlew test
```

### Create Executable JAR

```bash
./gradlew bootJar
```

The JAR will be created at `build/libs/sql-loader.jar`

### Run the SQL Loader

```bash
java -jar build/libs/sql-loader.jar \
  --url jdbc:postgresql://localhost:5432/mydb \
  --username myuser \
  --password mypassword \
  path/to/script.sql
```

#### CLI Options

- `<sqlFile>` - Path to the SQL file to execute (required)
- `-u, --url` - JDBC URL (required)
- `-n, --username` - Database username (required)
- `-p, --password` - Database password (optional, defaults to empty string)
- `-h, --help` - Show help message
- `-V, --version` - Print version information

### Example SQL File

```sql
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL
);

INSERT INTO users (name, email) VALUES ('Alice', 'alice@example.com');
INSERT INTO users (name, email) VALUES ('Bob', 'bob@example.com');
```

## Development

### Project Structure

```
src/
├── main/
│   ├── kotlin/
│   │   └── ai/obstreperous/sqlloader/
│   │       ├── SqlLoaderApplication.kt    # Main Spring Boot application
│   │       ├── SqlLoaderCommand.kt        # Picocli CLI command
│   │       └── SqlLoaderService.kt        # SQL execution service
│   └── resources/
│       └── application.properties         # Application configuration
└── test/
    ├── kotlin/
    │   └── ai/obstreperous/sqlloader/
    │       └── SqlLoaderServiceTest.kt    # Service tests with Testcontainers
    └── resources/
        └── application-test.properties    # Test configuration
```

### Running Tests

Tests use [Testcontainers](https://www.testcontainers.org/) to spin up a PostgreSQL container for integration testing:

```bash
./gradlew test
```

### Building for Production

Create an optimized JAR for production:

```bash
./gradlew clean bootJar
```

## Using with Docker

Create a `Dockerfile`:

```dockerfile
FROM eclipse-temurin:17-jre-alpine
COPY build/libs/sql-loader.jar /app/sql-loader.jar
WORKDIR /app
ENTRYPOINT ["java", "-jar", "sql-loader.jar"]
```

Build and run:

```bash
docker build -t sql-loader .
docker run --rm sql-loader \
  --url jdbc:postgresql://host.docker.internal:5432/mydb \
  --username myuser \
  --password mypassword \
  /path/to/script.sql
```

## Using with Kubernetes

Example Kubernetes Job:

```yaml
apiVersion: batch/v1
kind: Job
metadata:
  name: sql-loader-job
spec:
  template:
    spec:
      containers:
      - name: sql-loader
        image: sql-loader:latest
        args:
          - "--url"
          - "jdbc:postgresql://postgres-service:5432/mydb"
          - "--username"
          - "myuser"
          - "--password"
          - "$(DB_PASSWORD)"
          - "/scripts/init.sql"
        env:
        - name: DB_PASSWORD
          valueFrom:
            secretKeyRef:
              name: db-secret
              key: password
        volumeMounts:
        - name: sql-scripts
          mountPath: /scripts
      volumes:
      - name: sql-scripts
        configMap:
          name: sql-scripts
      restartPolicy: Never
  backoffLimit: 3
```

## Contributing

### Code Style

- Follow official Kotlin coding conventions
- Use idiomatic Kotlin features (data classes, extension functions, null safety)
- Prefer immutability (`val` over `var`)
- Keep functions small and focused on a single responsibility

### Testing

- Write tests before implementation
- Use descriptive test names that explain the scenario and expected outcome
- Follow AAA pattern: Arrange, Act, Assert

### Pull Requests

- Keep commits atomic and focused
- Write clear, descriptive commit messages
- Ensure all tests pass before submitting
- Include tests for new features

## License

This project is licensed under the Apache License 2.0 - see the LICENSE file for details.

## Architecture

### Design Principles

1. **Minimalism**: Keep the codebase lean and focused
2. **Test-First Development**: Write tests before implementation
3. **High Code Quality**: Maintain clean, readable code
4. **Container-Ready**: Optimize for minimal size and fast startup

### Technology Stack

- **Kotlin**: Modern JVM language with concise syntax
- **Spring Boot**: Lightweight framework for dependency injection
- **Picocli**: Powerful command-line interface framework
- **JDBC**: Direct database access without ORM overhead
- **Testcontainers**: Docker-based integration testing
- **Gradle**: Build automation with Kotlin DSL

## Troubleshooting

### Common Issues

**Problem**: "Failed to determine a suitable driver class"
- **Solution**: Ensure PostgreSQL driver is on the classpath (it's included in the JAR)

**Problem**: Connection timeout
- **Solution**: Verify database is accessible and credentials are correct

**Problem**: SQL syntax error
- **Solution**: Check SQL file syntax and ensure it's compatible with your database

### Getting Help

- Check existing [GitHub Issues](https://github.com/obstreperous-ai/sql-loader-kotlin-spring/issues)
- Open a new issue with reproduction steps
- Include error messages and relevant configuration

## Roadmap

Future enhancements:

- [ ] Support for multiple database types (MySQL, Oracle, etc.)
- [ ] Advanced SQL parsing (handling complex statements)
- [ ] Dry-run mode
- [ ] Progress reporting for large scripts
- [ ] Variable substitution in SQL files
- [ ] Parallel execution support

